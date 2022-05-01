package com.example.Web4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Web4Application {

	public static void main(String[] args) {
		SpringApplication.run(Web4Application.class, args);
	}
}
