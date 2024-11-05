package com.api.RecordTimeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RecordTimelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordTimelineApplication.class, args);
	}

}
