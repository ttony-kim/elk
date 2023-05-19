package com.elk.log.config;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;

import com.elk.log.elastic.ElasticDto;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

//@Configuration
public class ElasticSearchConfig {

	void test () throws ElasticsearchException, IOException {
		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
		
		// Create the transport with a Jackson mapper
		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

		// And create the API client
		ElasticsearchClient client = new ElasticsearchClient(transport);
		
		SearchResponse<ElasticDto> search = client.search( s -> s.index("test5").query(q -> q.term(t -> t.field("name").value(v -> v.stringValue("bicycle")))), ElasticDto.class);
		
				
		
	}

}
