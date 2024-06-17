package com.custom.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class DemoCustomSchedulerApplicationTests {

	@Test
	void contextLoads() {
		
//		try {
			int a = 2;
			int b = 1;
			String str = "" ;
			Assert.isTrue(a==b, "a랑b는 다르지");
			Assert.notNull(str, "str isNull");
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("error " + e.getMessage());
//		}
		
		System.out.println("done");
	}

}
