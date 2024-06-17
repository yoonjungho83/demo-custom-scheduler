package com.custom.scheduler.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custom.scheduler.model.SchedulerVo;
import com.custom.scheduler.service.SchedulerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("sch")
@RequiredArgsConstructor
public class SchedulerController {

	private final SchedulerService schedulerService;
	
	@GetMapping
	public Object selectSchedulerList() {
		return schedulerService.selectSchedulerList();
		
	}
	@GetMapping("/{classNm}/{fnNm}")
	public Object selectScheduler(@PathVariable("classNm") String classNm , @PathVariable("fnNm") String fnNm) {
		
		SchedulerVo vo = SchedulerVo.builder().classNm(classNm).schdlNm(fnNm).build();
		
		return schedulerService.selectScheduler(vo);
	}
	@PostMapping
	public Object saveScheduler(@RequestBody SchedulerVo vo) {
		
		return schedulerService.saveScheduler(vo);
	}
	@PutMapping
	public Object modifyScheduler(@RequestBody SchedulerVo vo) {
		
		return schedulerService.modifyScheduler(vo);
	}
	@DeleteMapping
	public Object deleteScheduler(@RequestBody SchedulerVo vo) {
		
		return schedulerService.deleteScheduler(vo);
	}
}
