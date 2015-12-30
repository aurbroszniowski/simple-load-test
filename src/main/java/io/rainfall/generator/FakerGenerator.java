package io.rainfall.generator;

import io.rainfall.ObjectGenerator;

import com.github.javafaker.Faker;

import java.util.Random;

/**
 * Generates Faker type data
 *
 * @author Aurelien Broszniowski
 */
public class FakerGenerator implements ObjectGenerator<Faker> {

  public Faker generate(final Long aLong) {
    return new Faker(new SeedRandom(aLong));
  }

  public String getDescription() {
    return "com.github.javafaker.Faker";
  }


  /**
   * This Random implementation returns always the same value : the seed
   * This is because Faker will use a Random object to calculate its values, and doesn't accept a seed
   * but for Rainfall generatos we want to be deterministic regarding the given seed, in order to allow
   * verifications. (e.g. if we put a value for seed = 1, we should be able to read the same value later for seed = 1)
   */
  private class SeedRandom extends Random {
    private final Long seed;

    public SeedRandom() {
      this.seed = 0L;
    }

    public SeedRandom(final long seed) {
      this.seed = seed;
    }

    @Override
    public synchronized void setSeed(final long seed) {
      super.setSeed(seed);
    }

    @Override
    protected int next(final int bits) {
      return seed.intValue();
    }

    @Override
    public void nextBytes(final byte[] bytes) {
    }

    @Override
    public int nextInt() {
      return seed.intValue();
    }

    @Override
    public int nextInt(final int n) {
      return seed.intValue() % n;
    }

    @Override
    public long nextLong() {
      return seed;
    }

    @Override
    public boolean nextBoolean() {
      return (seed % 2) == 0;
    }

    @Override
    public float nextFloat() {
      return seed.floatValue();
    }

    @Override
    public double nextDouble() {
      return seed.doubleValue();
    }

    @Override
    public synchronized double nextGaussian() {
      return seed.doubleValue();
    }
  }
}
