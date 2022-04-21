package io.github.richardstartin.galena;

public class Person {

  private final String firstName;
  private final String lastName;
  private final int age;
  private final int rank;
  private final int salary;
  private final double score;

  public Person(String firstName, String lastName, int age, int rank, int salary, double score) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.rank = rank;
    this.salary = salary;
    this.score = score;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }

  public int getLevel() {
    return rank;
  }

  public int getSalary() {
    return salary;
  }

  public double getScore() {
    return score;
  }
}
