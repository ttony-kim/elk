package com.elk.log.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties({ "@version", "logger_name", "level_value" })
public class ElasticDto {
	
	private String level;
	
	private String thread_name;
	
	private String message;
	
	@JsonProperty("@timestamp")
	private String timestamp;
	
}
