package com.elk.log.web;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class LogDto {
	
	private String className;
	private String methodName;
	private String aspectType;
	private List<Map<String, Object>> paramList;
	private String time;
	
}
