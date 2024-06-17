package com.custom.scheduler.listener;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.custom.scheduler.handler.SchedulerHandler;
import com.custom.scheduler.model.SchedulerVo;
import com.custom.scheduler.service.SchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private final SchedulerHandler schedulerHandler; 
	private final SchedulerService schedulerService; 
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		
		log.info("SchedulerListener start");
		
		init();
	}

	
	public void init() {
		
		List<SchedulerVo> list = schedulerService.selectSchedulerList();
		log.info("init size ={}  list = {}", list.size(), list);
		for(SchedulerVo vo :list) {
			schedulerHandler.initScheduler(vo);
		}
	}
	
}
