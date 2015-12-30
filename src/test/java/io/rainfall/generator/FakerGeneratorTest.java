package io.rainfall.generator;

import io.rainfall.ObjectGenerator;
import org.junit.Test;

import com.github.javafaker.Name;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Aurelien Broszniowski
 */
public class FakerGeneratorTest {

  @Test
  public void testEqualGeneration() {
    FakerGenerator generator1 = new FakerGenerator();
    FakerGenerator generator2 = new FakerGenerator();
    Name generated1 = generator1.generate(10L).name();
    Name generated2 = generator2.generate(10L).name();
    assertThat(generated1.fullName(), is(equalTo(generated2.fullName())));
  }

  @Test
  public void testNotEqualGeneration() {
    FakerGenerator generator1 = new FakerGenerator();
    FakerGenerator generator2 = new FakerGenerator();
    Name generated1 = generator1.generate(10L).name();
    Name generated2 = generator2.generate(11L).name();
    assertThat(generated1.fullName(), is(not(equalTo(generated2.fullName()))));
  }

}
