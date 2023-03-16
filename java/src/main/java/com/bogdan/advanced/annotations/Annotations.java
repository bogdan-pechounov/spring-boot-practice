package com.bogdan.advanced.annotations;

import java.util.Arrays;

@VeryImportant
public class Annotations {
    @VeryImportant
    void hi(){
        System.out.println("Hi");
    }

    @RunImmediately(times = 5)
    void hello(){
        System.out.println("Hello");
    }
    public static void main(String[] args) throws NoSuchMethodException {
        var annotations = new Annotations();
        System.out.println(Arrays.toString(annotations.getClass().getAnnotations()));
        System.out.println(Annotations.class);
        if(annotations.getClass().isAnnotationPresent(VeryImportant.class)){
            System.out.println("Very important!");
        }

        System.out.println(Arrays.toString(annotations.getClass().getMethods()));
        var method = annotations.getClass().getDeclaredMethod("hi");
        System.out.println(method);
        System.out.println(method.isAnnotationPresent(VeryImportant.class));

        RunImmediately annotation = annotations.getClass().getDeclaredMethod("hello").getAnnotation(RunImmediately.class);
        System.out.println(annotation.times());
    }
}
