package com.HttpMonitorService.CheckService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class HttpMonitorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpMonitorServiceApplication.class, args);
	}

}
