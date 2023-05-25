package com.elk.log.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;

import com.elk.log.elastic.ElasticDto;
import com.fasterxml.jackson.core.JsonGenerator;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.core.ScrollRequest;
import co.elastic.clients.elasticsearch.core.ScrollResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.sql.ElasticsearchSqlClient;
import co.elastic.clients.elasticsearch.sql.QueryResponse;
import co.elastic.clients.elasticsearch.sql.TranslateResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

public class ElasticUtil {
	
//	private static ElasticsearchClient client;
	
	public static void main(String[] args) throws ElasticsearchException, IOException {
//	private ElasticUtil() {
		// create the low-level client
		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
		// create the transport with a jackson mapper
		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
		// create the api client
		ElasticsearchClient client = new ElasticsearchClient(transport);
		
		
//		 query
//		System.out.println(client.exists(b -> b.index("test5").id("uoecHogBxpflzx0GBndG")).value());
//		QueryResponse response = client.sql().query(q -> q.query("SELECT * FROM test5"));
//		System.out.println(response);
//		TranslateResponse response = client.sql().translate(t -> t.query("SELECT * FROM test5 order by \"@timestamp\"").fetchSize(10));
//		System.out.println(response.toString());
		
//		SearchRequest.of(s -> s.withJson(null))
		
//		System.out.println(response.cursor());
//		System.out.println(response.rows());
//		for(Column c: response.columns()) {
//			System.out.println(c.name());
//		}
//		System.out.println(client.sql().query(q -> q.query("SELECT * FROM test5")));
		
		/*QueryResponse response = client.sql().query(q -> q.query("SELECT * FROM test5")
				.fetchSize(10)
				.cursor("t8jsA0RGTACEkktugzAQhm2Xqgld9Ai9QoraKstA3pGCBDHQZINobB6RgQi7aZuz9Bi9X2qTF7v+G/6Zf2YsfQKuAEwBgkDpIPVQOwB0CPU4o4yEvKyEFpJyfThOKcFfeAMQkqZeNaSrZciOugLbek9kOeUiyrfwkYsqW4uQRIKG5VZkZRGxUOVhERUlR63ejlZctgHS4qrMAbpldEcZQPf1N9xF7IOqqkwSWsmtXFZ38j6PEuk0nu1VLNKKRuQUwx8ouhtufg5HZOzvycAL5p0ZdodPS994WiwmI4tMvM7SswMX233PMQPXm80p6xr2mHmeJ/p4MH15xyR1NuzVccyLxtYqxsz6xjkpAn80Xb2lA3tjT9zcf8bYtUxz+N9bJtBPwNswbsJqKUqKjUQZX8BATdAvoVo1IKixskhUeQR1jZvArlNNcNfZM8DGcQWysdYAeh5C6jf4AwAA//8DAA==")
				);
		System.out.println(response);
		System.out.println(response.cursor());
		*/
		
		/* 기존 search
		SearchRequest sr = SearchRequest.of(s -> s.index("test5"));
		SearchRequest sr = SearchRequest.of(s -> s.index("test5").sort(d -> d.field(f -> f.field("@timestamp").order(SortOrder.Desc))));
		
		SearchResponse<ElasticDto> sre = client.search(sr, ElasticDto.class);
		
		List<Hit<ElasticDto>> hits = sre.hits().hits();
		for(Hit<ElasticDto> object: hits) {
			System.out.println(object.source());
		}
		
		System.out.println(sr);
		*/
		
//		 scroll
		/*SearchRequest sr = SearchRequest.of(s -> s.index("test5").size(10).scroll(scr -> scr.time("5m")));
		SearchResponse<ElasticDto> sre = client.search(sr, ElasticDto.class);
		
		String scrollId = sre.scrollId();
		List<Hit<ElasticDto>> searchHits = sre.hits().hits();
		print(searchHits);
		
		while(searchHits != null && searchHits.size() > 0) {
			ScrollRequest scr = nextScrollRequest(scrollId);
			ScrollResponse<ElasticDto> scre = client.scroll(scr, ElasticDto.class);
			scrollId = scre.scrollId();
			searchHits = scre.hits().hits();
			print(searchHits);
		}*/
		
		// from, size
//		SearchRequest sr = SearchRequest.of(s -> s.index("test5").sort(d -> d.field(f -> f.field("@timestamp").order(SortOrder.Asc))).from(50).size(10));
		SearchRequest sr = SearchRequest.of(s -> s.index("test5").sort(d -> d.field(f -> f.field("@timestamp").order(SortOrder.Asc))).from(30).size(10)
				.query(q -> q.term(t -> t.field("thread_name").value("main"))));
		
		SearchResponse<ElasticDto> sre = client.search(sr, ElasticDto.class);
		long a = sre.hits().total().value();
		System.out.println(a);
		List<Hit<ElasticDto>> hits = sre.hits().hits();
		for(Hit<ElasticDto> object: hits) {
			System.out.println(object.source());
		}
		
		SearchSourceBuilder ssb = new SearchSourceBuilder()
				.size(10)
				.query(QueryBuilders.matchQuery("thread_name", "main"))
				.sort(SortBuilders.fieldSort("@timestamp").order(org.elasticsearch.search.sort.SortOrder.DESC));
		
		org.elasticsearch.action.search.SearchRequest sssr = new org.elasticsearch.action.search.SearchRequest("test5").source(ssb);
		
//		SearchResponse<ElasticDto> searchResponse = client.search(sssr, ElasticDto.class);
		
		
		System.out.println("end");
		
	}
	
	 private static ScrollRequest nextScrollRequest(final String scrollId) {
	    return ScrollRequest
	        .of(scrollRequest -> scrollRequest.scrollId(scrollId).scroll(Time.of(t -> t.time("5m"))));
	  }
	
	private static void print(List<Hit<ElasticDto>> searchHits) {
		for(Hit<ElasticDto> dto : searchHits) {
			System.out.println(dto);
		}
		System.out.println("===========================");
	}
	

}
