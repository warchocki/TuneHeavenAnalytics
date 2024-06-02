package com.comapny.tuneheavenanalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TuneHeavenAnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuneHeavenAnalyticsApplication.class, args);
	}
}
