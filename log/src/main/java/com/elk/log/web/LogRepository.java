package com.elk.log.web;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elk.log.elastic.ElasticDto2;

import co.elastic.clients.elasticsearch.ml.Page;

@Repository
public interface LogRepository extends ElasticsearchRepository<ElasticDto2, String> {

}
