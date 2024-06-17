package com.custom.scheduler.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.custom.scheduler.mapper.SchedulerMapper;
import com.custom.scheduler.model.SchedulerVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

	private final SchedulerMapper schedulerMapper;
	
	private static final Double NANOS = 1000000000.0;
	
	
	public List<SchedulerVo> selectSchedulerList() {
		List<SchedulerVo> res = schedulerMapper.selectSchedulerList();
		return res;
	}
	public SchedulerVo selectScheduler(SchedulerVo vo) {
		SchedulerVo res = schedulerMapper.selectScheduler(vo);
		return res;
	}
	public SchedulerVo selectSchedulerForUpdate(SchedulerVo vo) {
		SchedulerVo res = schedulerMapper.selectSchedulerForUpdate(vo);
		return res;
	}
	
	
	
	
	
	public Integer saveScheduler(SchedulerVo vo) {
		Integer res = schedulerMapper.saveScheduler(vo);
		return res;
	}
	public Integer modifyScheduler(SchedulerVo vo) {
		
		log.info("modify start {}/{}" , vo.getClassNm(),vo.getSchdlNm());
		
		OffsetDateTime now = OffsetDateTime.now();
		OffsetDateTime eTime = vo.getLockEnd();
		Double lTime = vo.getLockTime();
		
		long plusSec = (long)(NANOS*lTime);
		vo.setLockStart(now);
		vo.setLockEnd(now.plusNanos(plusSec));
		vo.setMdfr("scheduler system");
		
		Integer res = schedulerMapper.modifyScheduler(vo);
		
		log.info("modify end {}/{}" , vo.getClassNm(),vo.getSchdlNm());
		return res;
	}
	public Integer deleteScheduler(SchedulerVo vo) {
		
		Integer res = schedulerMapper.deleteScheduler(vo);
		return res;
	}
	
}
