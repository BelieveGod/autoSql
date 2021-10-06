package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
//        String[] arr = new String[]{
//                "a", "b", "c"
//        };
        Integer[] integers = new Integer[4];
        integers[0] = 2;
        integers[1] = 21;
        integers[2] = 23;
        integers[3] = 24;
        format("fdf", integers);
        System.out.println("String.format(\"a:%s\", new Date()) = " + String.format("a:%s"));

    }

    public static String format(String format,Integer... objects){
        for (Object object : objects) {
            System.out.println("object.toString() = " + object.toString());
        }
        return format;
    }

}
