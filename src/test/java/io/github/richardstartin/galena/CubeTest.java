package io.github.richardstartin.galena;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CubeTest {

  @Test
  public void basicCheck() {
    Cube<Person> cube = Cube.builder(Person.class)
            .withDimension(Person::getFirstName)
            .withDimension(Person::getLastName)
            .withDimension(Person::getAge)
            .withDimension(Person::getLevel)
            .withMeasure(Person::getSalary, Integer::sum)
            .withMeasure(Person::getScore, Double::sum)
            .withMeasure(Person::getScore, Double::max)
            .build();
    cube.accept(new Person("John", "Little", 30, 5, 50000, 0.9D));
    cube.accept(new Person("John", "Doe", 35, 5, 45000, 0.75D));
    cube.accept(new Person("John", "Doe", 35, 5, 95000, 0.55D));
    assertEquals(cube.getAsInt(0, 0, 0, 2, 0, 3, 0), 50000);
    assertEquals(cube.getAsInt(0, 0, 0, 3, 0), 190000);
    assertEquals(cube.getAsDouble(1, 0, 0, 3, 0), 2.2D);
    assertEquals(cube.getAsDouble(2, 0, 0, 3, 0), 0.9D);
  }

}