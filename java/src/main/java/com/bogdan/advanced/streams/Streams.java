package com.bogdan.advanced.streams;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class Streams {
    @Test
    void person() {
        var maria = new Person("Maria", Gender.FEMALE);
        System.out.println(maria);
    }

    @Test
    void stream() {
        var people = List.of(new Person("John", Gender.MALE),
                new Person("Maria", Gender.FEMALE),
                new Person("Aisha", Gender.FEMALE),
                new Person("Alex", Gender.MALE),
                new Person("Alice", Gender.FEMALE));
        System.out.println(people.stream());
        var names = people.stream()
                .map(person -> person.name)
//                .map(String::length)
                .collect(Collectors.toSet());
        System.out.println(names);
    }

    static class Person {
        private final String name;
        private final Gender gender;

        Person(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "Person: " + "name=" + name + ", gender=" + gender;
        }
    }

    enum Gender {
        MALE, FEMALE
    }
}
