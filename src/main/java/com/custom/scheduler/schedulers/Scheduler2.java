package com.custom.scheduler.schedulers;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler2 {

	
	public void fn1() {
		log.info("Scheduler2 fn1 exec!!!");
	}
	public void fn2() {
		log.info("Scheduler2 fn2 exec!!!");
	}
	public void fn3() {
		log.info("Scheduler2 fn3 exec!!!");
	}
	
}
