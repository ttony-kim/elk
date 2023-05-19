package com.elk.log.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(indexName = "test5")
public class ElasticDto2 {
	
	@Id
	private String _id;
	
	@Field
	private String level;
	
	@Field
	private String thread_name;
	
	@Field
	private String message;
	
	
}
