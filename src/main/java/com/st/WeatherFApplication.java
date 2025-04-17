package com.st;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WeatherFApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherFApplication.class, args);
	}

}
