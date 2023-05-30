package com.elk.log.web;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elk.log.elastic.ElasticDto;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogController {
	
	public final LogService logService;
	public final ElasticsearchClient client;
	
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
	
	@GetMapping("/log/test")
	public String test() throws ElasticsearchException, IOException {
		SearchRequest sr = SearchRequest.of(s -> s.index("test5"));
		SearchResponse<ElasticDto> sre = client.search(sr, ElasticDto.class);
		List<Hit<ElasticDto>> hits = sre.hits().hits();
		for(Hit<ElasticDto> object: hits) {
			System.out.println(object.source());
		}
		return "test";
	}
	
	@GetMapping("/log/page")
	public String api() {
		Pageable page = PageRequest.of(9990, 10, Sort.by(Sort.Direction.DESC, "@timestamp"));
		log.info("pageSize = {}, pageNo = {}", page.getPageSize(), page.getPageNumber());
		logService.findAll2(page);
		return "api";
	}
	
	@GetMapping("/log/find2")
	public String find2() {
		logService.findAll3();
		return "find2";
	}
	
	@GetMapping("/log/findByThreadName")
	public String findByThreadName() {
		logService.findByThreadName();
		return "findByThreadName";
	}
	
	@GetMapping("/log/scroll")
	public String scroll() {
		logService.scroll();
		return "scroll";
	}
	
	@GetMapping("/log/scroll2")
	public String scroll2(int pageIndex, int pageSize) {
		logService.scroll2(pageIndex, pageSize);
		return "scroll2";
	}
	
	@GetMapping("/log/searchBy")
	public String searchBy(Pageable pageable) {
		logService.searchBy(pageable);
		return "searchBy";
	}
	
	@GetMapping("/log/criteria")
	public String criteria() {
		logService.criteria();
		return "criteria";
	}
	
	@GetMapping("/log/findBy")
	public String findBy(int pageIndex, int pageSize) {
		logService.findBy(pageIndex, pageSize);
		return "findBy";
	}
	
	@GetMapping("/log/findbyContaining")
	public String findbyContaining() {
		logService.findbyContaining();
		return "findbyContaining";
	}
	
	@GetMapping("/log/sort")
	public String sort() {
		logService.sort();
		return "sort";
	}
	
	@GetMapping("/log/final")
	public String finalTest() {
		logService.finaltest();
		return "finalTest";
	}
}
