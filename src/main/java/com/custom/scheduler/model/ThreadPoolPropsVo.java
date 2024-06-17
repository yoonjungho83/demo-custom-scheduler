package com.custom.scheduler.model;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ThreadPoolPropsVo {

	private String key;
	private ThreadPoolTaskScheduler scheduler;
	private Integer curVersion;
}
