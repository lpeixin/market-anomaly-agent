package com.liupeixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    // TODO load .env file

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
