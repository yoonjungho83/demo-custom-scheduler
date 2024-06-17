package com.custom.scheduler.schedulers;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.custom.scheduler.model.SchedulerVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Scheduler1 {

	
	
	public void fn1() {
		log.info("Scheduler1 fn1 exec!!!");
	}
	public void fn2() {
		log.info("Scheduler1 fn2 exec!!!");
	}
	public void fn3() {
		log.info("Scheduler1 fn3 exec!!!");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Data
	@AllArgsConstructor
	public class Dto{
		private Integer id;
		private OffsetDateTime execTime;
		private Long offset;
		private String robotInfo;
	}
	ThreadPoolTaskScheduler scheduler;
	public void test() {
		
		OffsetDateTime od = OffsetDateTime.now();
		List<Dto> list = new ArrayList<>();
		for(int i = 0 ; i < 100 ; i++) {
			Long offset = 0L;
			if(i== 0) {
				offset = null;
				list.add(new Dto(i , od, null , "robot"+i));
			}
			else {
				offset = (long)(Math.random()*100);
				Long sec = (long)(Math.random()*100);
				list.add(new Dto(i , od.plusSeconds(sec) , offset , "robot"+i));
			}
		}
		
		
		scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		scheduler.schedule(start(list), getTrigger(""));
		
	}
	
	public void isStop(boolean flag) {
		if(flag) scheduler.shutdown();
	}
	
	public Runnable start(List<Dto> list) {
		
		//scheduler 실행 전 cron으로 scheduler 실행 텀을 처리함.
		return () -> {
			//data loop 돌면서 해당 기준시간에 맞으면 함수 실행해줌.
			for(Dto dot : list) {
				
				while(true) {
					//현재시간과 Data 의 시간 비교 및 시간 true 시 함수 실행
					//redisSave(dto)
					//마지막 데이터의 호출이면 --->  isStop(true); 실행
					int a =0;
					int b =0;
					if(a==b) {
						redisSave(dot);
						break;
					}
				}
			}
			
			
			
		};
	}
	//cron 설정
	public Trigger getTrigger(String corn) {
		
		return new CronTrigger(corn);
	}
	
	public void redisSave(Dto dto) {
		
	}
}
