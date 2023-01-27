package com.test.log4j2.log4j2SPRING;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Log4j2SpringApplication {

	public static void main(String[] args) {
		System.setProperty("mail.smtp.starttls.enable", "true");
		SpringApplication.run(Log4j2SpringApplication.class, args);
	}

}
