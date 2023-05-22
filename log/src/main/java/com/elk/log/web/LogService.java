package com.elk.log.web;

import java.util.List;

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
		List<ElasticDto2> list = page.getContent();
		long total = page.getTotalElements();
		int cnt = 0;
		for(ElasticDto2 dto : page) {
			cnt ++;
			System.out.println(dto.toString());
		}
		System.out.println(cnt);
	}
	
	public void findAll3() {
		List<ElasticDto2> list = logRepositoy.findAll();
//		list.
		for(ElasticDto2 dto : list) {
			System.out.println(dto.toString());
		}
	}
	
	public void findByThreadName() {
		List<ElasticDto2> list = logRepositoy.findByThreadName("main");
		for(ElasticDto2 dto : list) {
			System.out.println(dto.toString());
		}
	}
}
