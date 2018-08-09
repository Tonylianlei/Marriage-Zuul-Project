package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableCircuitBreaker
@SpringBootApplication
public class MarriageZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarriageZuulApplication.class, args);
	}
}
