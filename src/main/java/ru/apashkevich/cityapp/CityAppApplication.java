package ru.apashkevich.cityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityAppApplication.class, args);
	}

}
