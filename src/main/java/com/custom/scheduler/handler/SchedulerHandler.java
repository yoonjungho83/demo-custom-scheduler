package com.custom.scheduler.handler;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.custom.scheduler.enums.SchedulerEnum;
import com.custom.scheduler.model.SchedulerVo;
import com.custom.scheduler.model.ThreadPoolPropsVo;
import com.custom.scheduler.service.SchedulerService;
import com.custom.scheduler.support.DateUtil;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerHandler {

	
	private final ApplicationContext context;
	private final SchedulerService schedulerService ;
	
	//scheduler start / stop 제어용 맵
	private static Map<String , ThreadPoolPropsVo> tpMap = new HashMap<>();

	//scheduler 함수 호출용
	private static Map<Class , Object> schedulerMap = new HashMap<>();
	
	@PostConstruct
	public void initSchedulerAdd() {
		log.info("scheduler init ");
		
		
		for(final var scheduler : SchedulerEnum.values()) {
			final var key = scheduler.getSchedulerClass();
			this.schedulerMap.put(key, this.context.getBean(key));
		}
	}
	
	public void schedulerShutdownAll() {
		for(Map.Entry<String,ThreadPoolPropsVo> entry : tpMap.entrySet()) {
			entry.getValue().getScheduler().shutdown();
		}
	}
	
	public void schedulerShutdown(SchedulerVo vo) {
		SchedulerVo rv = schedulerService.selectScheduler(vo);
		String key = vo.getClassNm()+"_"+vo.getSchdlNm();
		ThreadPoolPropsVo sc = tpMap.get(key);
		
		if(sc == null) {
			return;
		}
		
		if(sc.getCurVersion() != vo.getSchdlVrsn()) {
			
			sc.getScheduler().shutdown();
			
			ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
			scheduler.initialize();
			
			ThreadPoolPropsVo tpp = ThreadPoolPropsVo.builder().key(key).scheduler(scheduler).curVersion(vo.getSchdlVrsn()).build();
			tpMap.put(key, tpp);
			tpp.getScheduler().schedule(getRunnable(vo), getTrigger(vo));
		}
		
	}
	
	public void initScheduler(SchedulerVo vo) {
		startScheduler(vo);
	}
	
	private void startScheduler(SchedulerVo vo) {
		System.out.println("start scheduler vo.class name / shdl name" + vo.getClassNm() +" / " + vo.getSchdlNm());
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.initialize();
		String key = vo.getClassNm() + "_" + vo.getSchdlNm();
		ThreadPoolPropsVo tpp = 
				ThreadPoolPropsVo.builder()
				.key(key)
				.scheduler(scheduler)
				.curVersion(vo.getSchdlVrsn())
				.build();
		tpp.getScheduler().schedule(getRunnable(vo), getTrigger(vo));
	}
	
	public Runnable getRunnable(SchedulerVo vo) {
		System.out.println("getRunnable !!!!!!!!!!!!!!"+ vo.getClassNm() +" / " + vo.getSchdlNm());
		return ()->{
			SchedulerVo v = schedulerService.selectScheduler(vo);
			
			try {
				if(!v.getUseYn().equals("Y")) {
					return;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			startRunnableSub(vo);
		};
	}
	
	public void startRunnableSub(SchedulerVo vo) {
		System.out.println("startRunnableSub !!!!!!!!!!!!!!"+ vo.getClassNm() +" / " + vo.getSchdlNm());
		SchedulerVo sVo = null;
		String addr = ""; 
				
		try {
			//for update 이유는 다른 인스턴스에서 같이 진입할 경우를 대비하여 행에 lock를 걸어주기 위해서
			sVo = schedulerService.selectSchedulerForUpdate(vo);
			
			addr = InetAddress.getLocalHost().toString();
			
			if(!setClassKey(sVo)) {
				log.error("error startRunnableSub class name = {} / fn name = {}" , vo.getClass() , vo.getSchdlNm());
				schedulerService.modifyScheduler(sVo);
				return;
			}
			
			Class key = sVo.getClassKey();
			Class<?> cls = context.getBean(key).getClass();
			Method m = cls.getMethod(sVo.getSchdlNm());
			
			//서버가 여러대인 경우 해당 함수가 현재 다른 서버에서 돌고 있으면돌리지 않아야함.
			OffsetDateTime now = OffsetDateTime.now(); 
			OffsetDateTime eTime = sVo.getLockEnd(); 
			Double lTime = sVo.getLockTime(); 
			
			int diff = DateUtil.compareNanois( eTime , now );
			log.info(" time diff ============> {} ",diff);
			if(vo.getLockYn().equals("Y") && diff > 0) {
				log.error("실행 시간이 안되어 실행 안됨 ::: {} {}" ,vo.getClassNm() , vo.getSchdlNm() );
			}
			else {
				log.info("실행 ::: {} {}" ,vo.getClassNm() , vo.getSchdlNm() );
				schedulerService.modifyScheduler(sVo);
				m.invoke(schedulerMap.get(key));
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			log.info("runnable 종료");
		}
				
		
	}
	
	
	public boolean setClassKey(SchedulerVo vo) {
		boolean flag = false;
		
		try {
			for(Map.Entry<Class,Object> entry : schedulerMap.entrySet()) {
				String [] keys = entry.getKey().toString().split("[.]");
				String className = keys[keys.length-1];
				if(vo.getClassNm().equals(className)) {
					vo.setClassKey(entry.getKey());
					flag = true;
					break;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return flag;
	}
	
	public Trigger getTrigger(SchedulerVo vo) {
		
		return new CronTrigger(vo.getCronExprs());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
