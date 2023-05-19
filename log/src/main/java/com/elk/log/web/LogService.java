package com.elk.log.web;

import org.springframework.stereotype.Service;

import com.elk.log.elastic.ElasticDto2;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
	
	private final LogRepository logRepositoy;
	
	public void findAll() {
		Iterable<ElasticDto2> iter = logRepositoy.findAll();
		int cnt = 0;
		for(ElasticDto2 dto : iter) {
			cnt ++;
			System.out.println(dto.toString());
		}
		System.out.println(cnt);
	}
}
