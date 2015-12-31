import io.rainfall.Runner;
import io.rainfall.Scenario;
import io.rainfall.SyntaxException;
import io.rainfall.configuration.ConcurrencyConfig;
import io.rainfall.ehcache.statistics.EhcacheResult;
import io.rainfall.ehcache2.CacheConfig;
import io.rainfall.generator.FakerGenerator;
import io.rainfall.generator.IntegerGenerator;
import io.rainfall.generator.sequence.Distribution;
import io.rainfall.statistics.StatisticsPeekHolder;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.TerracottaClientConfiguration;
import net.sf.ehcache.config.TerracottaConfiguration;

import com.github.javafaker.Faker;

import static io.rainfall.configuration.ReportingConfig.html;
import static io.rainfall.configuration.ReportingConfig.report;
import static io.rainfall.configuration.ReportingConfig.text;
import static io.rainfall.ehcache.statistics.EhcacheResult.GET;
import static io.rainfall.ehcache.statistics.EhcacheResult.MISS;
import static io.rainfall.ehcache.statistics.EhcacheResult.PUT;
import static io.rainfall.ehcache2.Ehcache2Operations.get;
import static io.rainfall.ehcache2.Ehcache2Operations.put;
import static io.rainfall.execution.Executions.during;
import static io.rainfall.execution.Executions.times;
import static io.rainfall.unit.TimeDivision.minutes;
import static java.util.concurrent.TimeUnit.MINUTES;
import static net.sf.ehcache.config.MemoryUnit.GIGABYTES;

/**
 * @author Aurelien Broszniowski
 */
public class SimpleLoad {

  public static void main(String[] args) {
    String tsaUrl = System.getProperty("tsa.url");
    Long nbElements = Long.parseLong(System.getProperty("nbElements", "5000"));

    new SimpleLoad().runLoad(tsaUrl, nbElements);
  }


  /**
   * This runs a simple cache load test
   *
   * @param tsaUrl     URL of the TSA
   * @param nbElements nbElements in the cache
   */
  private void runLoad(final String tsaUrl, final Long nbElements) {
    CacheManager cacheManager = null;
    try {
      Configuration configuration = new Configuration().name("SimpleLoad")
          .terracotta(new TerracottaClientConfiguration().url(tsaUrl))
          .defaultCache(new CacheConfiguration("default", 0).eternal(true))
          .cache(new CacheConfiguration().name("one")
              .maxBytesLocalHeap(1, GIGABYTES)
          .terracotta(new TerracottaConfiguration()));
      cacheManager = CacheManager.create(configuration);

      Ehcache one = cacheManager.getEhcache("one");

      ConcurrencyConfig concurrency = ConcurrencyConfig.concurrencyConfig().threads(4).timeout(50, MINUTES);

      IntegerGenerator keyGenerator = new IntegerGenerator();
      FakerGenerator valueGenerator = new FakerGenerator();

      Runner.setUp(
          Scenario.scenario("Warm up phase")
              .exec(
                  put().using(keyGenerator, valueGenerator).sequentially().withWeight(1.0)
              ))
          .executed(times(nbElements))
          .config(concurrency)
          .config(report(EhcacheResult.class, new EhcacheResult[] { PUT }).log(text()))
          .config(CacheConfig.<Integer, FakerGenerator>cacheConfig()
              .caches(one)
          )
          .start();

      System.out.println(one.getStatistics().getLocalHeapSize());
      System.out.println(one.getStatistics().getLocalOffHeapSize());
      System.out.println(one.getStatistics().getRemoteSize());

      StatisticsPeekHolder finalStats = Runner.setUp(
          Scenario.scenario("Test phase").exec(
              put().withWeight(0.90)
                  .atRandom(Distribution.GAUSSIAN, 0, nbElements, nbElements / 10)
                  .using(keyGenerator, valueGenerator),
              get().withWeight(0.10)
                  .atRandom(Distribution.GAUSSIAN, 0, nbElements, nbElements / 10)
                  .using(keyGenerator, valueGenerator)
          ))
          .warmup(during(5, minutes))
          .executed(during(15, minutes))
          .config(concurrency)
          .config(report(EhcacheResult.class, new EhcacheResult[] { PUT, GET, MISS }).log(text(), html()))
          .config(CacheConfig.<Integer, FakerGenerator>cacheConfig()
              .caches(one)
          )
          .start();

    } catch (SyntaxException e) {
      e.printStackTrace();
    } finally {
      if (cacheManager != null) {
        cacheManager.shutdown();
      }
    }
  }

}
