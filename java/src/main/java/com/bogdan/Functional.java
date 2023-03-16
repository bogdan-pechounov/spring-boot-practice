package com.bogdan;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Functional {
    @Test
    void immutableList() {
        var list = List.of("a", "b", "c");
        System.out.println(list.getClass());
        for (var s : list) {
            System.out.println(s);
        }
        list.add("d");
    }

    @Test
    void function() {
        Function<Integer, Integer> addOne = n -> n + 1;
        Function<Integer, Integer> multiplyBy10 = n -> n * 10;

        test(addOne);
        test(Functional::addOne);
        System.out.println(addOne.andThen(multiplyBy10).apply(1));
    }

    static int addOne(int a) {
        return a + 1;
    }

    static void test(Function<Integer, Integer> t) {
        System.out.println(t);
    }

    @Test
    void consumer() {
        Consumer<Customer> greetCustomer = customer -> {
            System.out.println(customer.name);
        };
        System.out.println(greetCustomer);
        var maria = new Customer("Maria", "435");
        greetCustomer.accept(maria);
    }

    static class Customer {
        private final String name;
        private final String phoneNumber;

        Customer(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
    }

    @Test
    void supplier(){
        Supplier<String> getConnectionString = () -> "http://";
        System.out.println(getConnectionString);
        System.out.println(getConnectionString.get());
    }

    @Test
    void callbacks(){
        hello("John", "", value -> System.out.println("no last name provided for: " + value));
    }

    static void hello(String firstName, String lastName, Consumer<String> callback){
        if(lastName != null){
            callback.accept(firstName);
        }
    }
}
