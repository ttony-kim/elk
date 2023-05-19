package com.elk.log.elastic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.elk.log.web.LogService;

@SpringBootTest
public class ElasticTestController {

	@Autowired
	private LogService logservice;
	
	@Test
	void test() {
		logservice.findAll();
	}
	

}
