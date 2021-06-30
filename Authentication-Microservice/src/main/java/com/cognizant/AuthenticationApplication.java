package com.cognizant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class AuthenticationApplication {
	public static void main(String[] args) {
		log.info("Started Authentication");
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}
