package com.custom.scheduler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.custom.scheduler.model.SchedulerVo;

@Mapper
@Repository
public interface SchedulerMapper {

	
	public List<SchedulerVo> selectSchedulerList();
	public SchedulerVo selectScheduler(SchedulerVo vo);
	public SchedulerVo selectSchedulerForUpdate(SchedulerVo vo);
	
	
	public Integer     saveScheduler(SchedulerVo vo);
	public Integer     modifyScheduler(SchedulerVo vo);
	public Integer     deleteScheduler(SchedulerVo vo);
	
}
