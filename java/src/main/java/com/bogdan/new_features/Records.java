package com.bogdan.new_features;

public class Records {
    public static void main(String[] args) {
        var p1 = new Point(10, 10);
        System.out.println(p1);
    }
}

record Point(int x, int y){}
