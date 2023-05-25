package com.elk.log.web;

import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elk.log.elastic.ElasticDto3;

@Repository
public interface LogRepository2 extends ElasticsearchRepository<ElasticDto3, Long> {

	Stream<ElasticDto3> searchBy(Pageable pageable);
	Stream<ElasticDto3> findBy(Pageable pageable);
	Stream<ElasticDto3> findByTextEntry(String textEntry);
	long countByTextEntry(String textEntry);
	
}
