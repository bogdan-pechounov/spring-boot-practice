package com.bogdan.new_features;

public class Switch {
    public static void main(String[] args) {
        System.out.println(test("a"));
    }

    static String test(Object o){
        return switch(o) {
            case String s when s.length() == 1 -> "length";
            case Integer i -> "integer";
            default -> o.toString();
        };
    }
}
