package com.cognizant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import jdk.jfr.Enabled;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZullApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZullApiGatewayApplication.class, args);
	}

}
