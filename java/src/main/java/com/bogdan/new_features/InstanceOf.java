package com.bogdan.new_features;

public class InstanceOf {
    public static void main(String[] args) {
        var obj = new Object();
        obj = "string";
//        if(obj instanceof String){
//            String s = (String) obj;
//            System.out.println(s);
//        }

        if(obj instanceof String s){
            System.out.println(obj + s);
        }
    }
}
