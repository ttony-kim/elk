package com.elk.log.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
//@EnableElasticsearchRepositories
public class EsConfig extends ElasticsearchConfiguration {

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()           
				.connectedTo("localhost:9200")
				.build();
	}
	
	
//	@Bean
//	public ElasticsearchClient client() {
//		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
//		
//		// Create the transport with a Jackson mapper
//		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//
//		// And create the API client
//		ElasticsearchClient client = new ElasticsearchClient(transport);
//		return client;
//	}
//	
//	@Bean
//	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
//        return new ElasticsearchRestTemplate(client());
//    }
}
