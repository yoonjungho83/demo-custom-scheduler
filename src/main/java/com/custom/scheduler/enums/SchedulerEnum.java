package com.custom.scheduler.enums;

import com.custom.scheduler.schedulers.Scheduler1;
import com.custom.scheduler.schedulers.Scheduler2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SchedulerEnum {

	
	 scheduler1(Scheduler1.class)
	,scheduler2(Scheduler2.class)
	;
	
	
	private final Class<?> schedulerClass;
	
	public String getId() {
		return this.name();
	}
}
