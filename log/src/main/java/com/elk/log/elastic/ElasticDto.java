package com.elk.log.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@Document(indexName = "test5")
@JsonIgnoreProperties({ "@version", "logger_name", "level_value" })
public class ElasticDto {
	
//	@Id
//	private String _id;
	
//	@Field
	private String level;
	
//	@Field
	private String thread_name;
	
//	@Field
	private String message;
	
	@JsonProperty("@timestamp")
//	@Field(type = FieldType.Date, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//	@Field
	private String timestamp;
	
}
