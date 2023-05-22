package com.elk.log.web;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elk.log.elastic.ElasticDto2;

@Repository
public interface LogRepository extends ElasticsearchRepository<ElasticDto2, String> {

	List<ElasticDto2> findAll(); 
	List<ElasticDto2> findByThreadName(String threadName1);
	
}
