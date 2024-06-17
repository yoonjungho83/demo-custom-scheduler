package com.custom.scheduler.support;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

	
	public static int compareNanois(OffsetDateTime date1 , OffsetDateTime date2) {
		OffsetDateTime datetime1 = date1.truncatedTo(ChronoUnit.NANOS);
		OffsetDateTime datetime2 = date2.truncatedTo(ChronoUnit.NANOS);
		int result = datetime1.compareTo(datetime2);
		
		return result;
		
	}
}
