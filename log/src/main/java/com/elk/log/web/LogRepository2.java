package com.elk.log.web;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elk.log.elastic.ElasticDto3;

@Repository
public interface LogRepository2 extends ElasticsearchRepository<ElasticDto3, Long> {

	Stream<ElasticDto3> searchBy(Pageable pageable);
	Stream<ElasticDto3> findBy(Pageable pageable);
	Stream<ElasticDto3> findByTextEntry(String textEntry);
	Stream<ElasticDto3> findByTextEntryContaining(String textEntry);
	Stream<ElasticDto3> findByTextEntryContaining(String textEntry, Sort sort);
	
	long count();
	long countByTextEntry(String textEntry);
	long countByTextEntryContaining(String textEntry);
	
	Stream<ElasticDto3> findBy(Sort sort);
	Stream<ElasticDto3> findByTextEntry(String textEntry, Sort sort);
	Stream<ElasticDto3> findByTextEntryOrderByDocIdDesc(String textEntry);
	
}
