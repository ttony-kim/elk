package com.elk.log.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Data;

@Data
@Document(indexName = "test5")
public class ElasticDto2 {
	
	@Id
	private String _id;
	
	@Field
	private String level;
	
	@Field(name="thread_name")
	private String threadName;
	
	@Field
	private String message;
	
	@Field(name="@timestamp")
	private String time;
}
