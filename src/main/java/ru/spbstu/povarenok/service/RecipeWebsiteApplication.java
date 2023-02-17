package ru.spbstu.povarenok.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"ru.spbstu.povarenok"})
public class RecipeWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeWebsiteApplication.class, args);
    }

}
