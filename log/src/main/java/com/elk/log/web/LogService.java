package com.elk.log.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public void findAll2(Pageable pageable) {
		Page<ElasticDto2> page = logRepositoy.findAll(pageable);
	}
}
