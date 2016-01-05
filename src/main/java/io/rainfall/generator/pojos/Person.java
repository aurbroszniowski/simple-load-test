package io.rainfall.generator.pojos;

import com.github.javafaker.Faker;

/**
 * Created by Anthony Dahanne on 2016-01-05.
 */
public class Person {

  private final String firstName;
  private final String lastName;
  private final String emailAddress;
  private final String image;
  private final String streetAddress;
  private final String city;
  private final String zipCode;
  private final String country;
  private final String creditCard;
  private final String companyName;

  public Person(String firstName, String lastName, String emailAddress, String image, String streetAddress, String city, String zipCode, String country, String creditCard, String companyName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.image = image;
    this.streetAddress = streetAddress;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
    this.creditCard = creditCard;
    this.companyName = companyName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
    if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
    if (emailAddress != null ? !emailAddress.equals(person.emailAddress) : person.emailAddress != null) return false;
    if (image != null ? !image.equals(person.image) : person.image != null) return false;
    if (streetAddress != null ? !streetAddress.equals(person.streetAddress) : person.streetAddress != null)
      return false;
    if (city != null ? !city.equals(person.city) : person.city != null) return false;
    if (zipCode != null ? !zipCode.equals(person.zipCode) : person.zipCode != null) return false;
    if (country != null ? !country.equals(person.country) : person.country != null) return false;
    if (creditCard != null ? !creditCard.equals(person.creditCard) : person.creditCard != null) return false;
    return companyName != null ? companyName.equals(person.companyName) : person.companyName == null;

  }

  @Override
  public int hashCode() {
    int result = firstName != null ? firstName.hashCode() : 0;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
    result = 31 * result + (image != null ? image.hashCode() : 0);
    result = 31 * result + (streetAddress != null ? streetAddress.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (creditCard != null ? creditCard.hashCode() : 0);
    result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Person{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", emailAddress='" + emailAddress + '\'' +
        ", image='" + image + '\'' +
        ", streetAddress='" + streetAddress + '\'' +
        ", city='" + city + '\'' +
        ", zipCode='" + zipCode + '\'' +
        ", country='" + country + '\'' +
        ", creditCard='" + creditCard + '\'' +
        ", companyName='" + companyName + '\'' +
        '}';
  }

  public static Person generateWithFaker(Faker faker) {
    return new Person(faker.name().firstName(),faker.name().lastName(), faker.internet().emailAddress(), faker.internet().image(), faker.address().streetAddress(false), faker.address().city(), faker.address().zipCode(),  faker.address().country(), faker.finance().creditCard(), faker.company().name());
  }

}

