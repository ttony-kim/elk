package com.elk.log.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogController {
	
	public final LogService logService;
	
	@GetMapping("/log")
	public String createLog() {
		log.info("createLog");
		
		return "success";
	}
	
	@GetMapping("/log/all")
	public String findAll() {
		log.info("findAll");
		logService.findAll();
		return "success";
	}
	
}
