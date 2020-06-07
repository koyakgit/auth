package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		log.info("Demo start !!!");
		SpringApplication.run(DemoApplication.class, args);
	}
}
