package com.custom.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT5M")
@SpringBootApplication
public class DemoCustomSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCustomSchedulerApplication.class, args);
	}

}
