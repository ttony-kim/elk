package com.elk.log.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Data;

@Document(indexName="shakespeare")
@Data
public class ElasticDto3 {
	
	@Id //필수
	@Field(name = "doc_id")
	private long docId;
	
	private String play_name;
	
	private String speaker;
	
	@Field(name="text_entry") //필수
	private String textEntry;
}
