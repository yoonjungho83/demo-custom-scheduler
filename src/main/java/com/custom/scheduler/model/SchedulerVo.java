package com.custom.scheduler.model;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
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
public class SchedulerVo {

	@NotNull
	private String classNm;
	@NotNull
	private String schdlNm;
	
	private String cronExprs;
	private String useYn;
	private String lockYn;
	private Integer exeOrd;
	private Integer schdlVrsn;
	
	private OffsetDateTime lockStart;
	private OffsetDateTime lockEnd;
	private String lockBy;
	private Double lockTime;
	
	private String crtr;
	private OffsetDateTime crtTime;
	private String mdfr;
	private OffsetDateTime mdfTime;
	
	private Class classKey;
	private String userId;
	
}
