package com.cognizant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@Slf4j
public class AccountserviceApplication {

	public static void main(String[] args) {
		log.info("Starting Account Microservice");
		SpringApplication.run(AccountserviceApplication.class, args);
	}

}