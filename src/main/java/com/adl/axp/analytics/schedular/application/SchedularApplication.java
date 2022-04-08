package com.adl.axp.analytics.schedular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = {"com.adl.axp.analytics.schedular"})
@SpringBootApplication
public class SchedularApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchedularApplication.class, args);
	}
}
