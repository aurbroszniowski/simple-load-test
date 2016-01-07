package io.rainfall.generator;

import io.rainfall.generator.pojos.Person;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Aurelien Broszniowski
 */
public class FakerGeneratorTest {


  @Test
  public void testGenerator() throws Exception {
    FakerGenerator fakerGenerator = new FakerGenerator();
    Person generate1 = fakerGenerator.generate(0L);
    Person generate2 = fakerGenerator.generate(0L);

    assertThat(generate1, is(not((generate2))));

  }

// FakerGenerator outcome is no longer "controllable"
//  @Test
//  public void testEqualGeneration() {
//    FakerGenerator generator1 = new FakerGenerator();
//    FakerGenerator generator2 = new FakerGenerator();
//    Name generated1 = generator1.generate(10L).name();
//    Name generated2 = generator2.generate(10L).name();
//    assertThat(generated1.fullName(), is(equalTo(generated2.fullName())));
//  }
//
//  @Test
//  public void testNotEqualGeneration() {
//    FakerGenerator generator1 = new FakerGenerator();
//    FakerGenerator generator2 = new FakerGenerator();
//    Name generated1 = generator1.generate(10L).name();
//    Name generated2 = generator2.generate(11L).name();
//    assertThat(generated1.fullName(), is(not(equalTo(generated2.fullName()))));
//  }

}
