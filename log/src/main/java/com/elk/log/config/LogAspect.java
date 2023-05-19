package com.elk.log.config;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.elk.log.util.LogUtil;
import com.elk.log.web.LogDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogAspect {
	@Pointcut("execution(* com.elk.log.web.*Controller.*(..))")
	private void logAspect() {}
	
	@Before(value="logAspect()")
	public void beforeAspect(JoinPoint joinPoint) {
		LogDto logDto = new LogDto();
		logDto.setAspectType("before");
		
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		logDto.setTime(currentTimestamp.toString());
		
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		logDto.setClassName(ms.getClass().getName());
		logDto.setMethodName(ms.getMethod().getName());
		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		Object[] args = joinPoint.getArgs();
		
		for(Object obj : args) {
			map.clear();
			map.put("type", obj.getClass().getSimpleName());
			map.put("value", obj.toString());
			list.add(map);
		}
		
		logDto.setParamList(list);
		
		LogUtil.printLog(logDto);
		log.info("{}", logDto);
		
	}
	
	@AfterReturning(value="logAspect()", returning="returnValue")
	public void afterReturningAspect(JoinPoint joinPoint, Object returnValue) throws RuntimeException {
		LogDto logDto = new LogDto();
		logDto.setAspectType("afterReturning");
		
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		logDto.setTime(currentTimestamp.toString());
		
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		logDto.setClassName(ms.getClass().getName());
		logDto.setMethodName(method.getName());
		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		
		Class returnParam = method.getReturnType();
		map.put("type", returnParam.getName());
		map.put("value", returnValue);
		list.add(map);
		
		logDto.setParamList(list);
		
		LogUtil.printLog(logDto);
		log.info("{}", logDto);
	}

	@AfterThrowing(value="logAspect()", throwing="exception")
	public void afterThrowingAspect(JoinPoint joinPoint, Exception exception) throws RuntimeException {
		LogDto logDto = new LogDto();
		logDto.setAspectType("afterThrowing");
		
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		logDto.setTime(currentTimestamp.toString());
		
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		logDto.setClassName(ms.getClass().getName());
		logDto.setMethodName(ms.getMethod().getName());
		
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		
		map.put("type", exception.getClass());
		map.put("value", exception.getMessage());
		list.add(map);
		
		logDto.setParamList(list);
		
		LogUtil.printLog(logDto);
		log.info("{}", logDto);
	}
	
}
