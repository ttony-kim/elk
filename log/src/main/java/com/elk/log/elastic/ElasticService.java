package com.elk.log.elastic;

import org.elasticsearch.client.RestHighLevelClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElasticService {
	
	private final RestHighLevelClient client;
	
	public void search() {
//		client.search(null, null);
	}

}
