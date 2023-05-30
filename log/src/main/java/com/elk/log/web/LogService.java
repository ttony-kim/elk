package com.elk.log.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AbstractElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitsIterator;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.elk.log.elastic.ElasticDto2;
import com.elk.log.elastic.ElasticDto3;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
	
	private final LogRepository logRepositoy;
	private final ElasticsearchOperations operations; 
	private final LogRepository2 logRepository2;
	
	public void findAll() {
		Iterable<ElasticDto2> iter = logRepositoy.findAll();
		int cnt = 0;
		for(ElasticDto2 dto : iter) {
			cnt ++;
			System.out.println(dto.toString());
		}
		System.out.println(cnt);
	}
	
	public void findAll2(Pageable pageable) {
		Page<ElasticDto2> page = logRepositoy.findAll(pageable);
		List<ElasticDto2> list = page.getContent();
		long total = page.getTotalElements();
		int cnt = 0;
		for(ElasticDto2 dto : page) {
			cnt ++;
			System.out.println(dto.toString());
		}
		System.out.println(cnt);
	}
	
	public void findAll3() {
		List<ElasticDto2> list = logRepositoy.findAll();
		for(ElasticDto2 dto : list) {
			System.out.println(dto.toString());
		}
	}
	
	public void findByThreadName() {
		List<ElasticDto2> list = logRepositoy.findByThreadName("main");
		for(ElasticDto2 dto : list) {
			System.out.println(dto.toString());
		}
	}
	
	public void scroll() {
		IndexCoordinates index = IndexCoordinates.of("test5");
		Query query = NativeQuery.builder()
			.withQuery(q -> q.matchAll(ma -> ma))
			.withFields("message")
			.withPageable(PageRequest.of(0, 10))
			.build();
		
		SearchHitsIterator<ElasticDto2> stream = operations.searchForStream(query, ElasticDto2.class,
				index);
		
		List<ElasticDto2> sampleEntities = new ArrayList<>();
		while (stream.hasNext()) {
			org.springframework.data.elasticsearch.core.SearchHit<ElasticDto2> ss= stream.next();
			sampleEntities.add(ss.getContent());
		}
		
		sampleEntities.forEach(m -> {
			System.out.println(m.toString());
		});
		System.out.println(sampleEntities.size());

		stream.close();
	}
	
	public void scroll2(int pageIndex, int pageSize) {
		int start = (pageIndex-1) * pageSize;
		int end = start + pageSize;
		/*
		long beforeTime = System.currentTimeMillis();
		IndexCoordinates index = IndexCoordinates.of("hakespeare");
		List<Order> orders = new ArrayList<>();
		Query query = NativeQuery.builder()
//			.withQuery(q -> q.matchAll(ma -> ma))
			.withQuery(q -> q.match(d -> d.field("text_entry").query("No")))
			.withPageable(PageRequest.of(0, 25))
			.withSort(Sort.by(Direction.DESC , "doc_id"))
			.build();
		
//		SearchHitsIterator<ElasticDto3> stream = operations.searchForStream(query, ElasticDto3.class,
//				index);
		
		SearchHitsIterator<ElasticDto3> stream = operations.searchForStream(query, ElasticDto3.class);
		
		System.out.println(stream.getTotalHits());
		long count = operations.count(query, ElasticDto3.class);
		System.out.println("count: " + count);
		
		List<ElasticDto3> sampleEntities = new ArrayList<>();
		int i = 0;
		System.out.println("start: " + start + " end: " + end);
		while (stream.hasNext()) {
			org.springframework.data.elasticsearch.core.SearchHit<ElasticDto3> ss= stream.next();
			System.out.println(ss);
			if(i >= start && i < end) {
				sampleEntities.add(ss.getContent());
			}
			if(i == end) {
				break;
			}
			i++;
		}
		System.out.println(i);
		
		sampleEntities.forEach(m -> {
			System.out.println(m.toString());
		});
		System.out.println(sampleEntities.size());

		stream.close();
		long afterTime = System.currentTimeMillis();
		long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
		System.out.println("시간차이(m) : "+secDiffTime);
		*/
		
	}
	
	public void searchBy(Pageable pageable) {
//		Stream<ElasticDto3> stream = logRepository2
//				.searchBy(PageRequest.of(0, 10, Sort.by(Direction.ASC , "doc_id")))
//				.skip(10000).limit(10); // 스트림 문법
//		System.out.println(stream);
		
		
		List<ElasticDto3> list = logRepository2.searchBy(PageRequest.of(0, 10)).skip(10000).limit(10).collect(Collectors.toList());
		list.forEach(m -> {
			System.out.println(m);
		});
	}
	
	public void criteria() {
		
//		Criteria criteria = new Criteria("text_entry").is("No");
//		Query query2 = new CriteriaQuery(criteria);
		
		Pageable page = PageRequest.of(0, 10, Sort.by(Direction.DESC , "doc_id"));
//		Criteria c = Criteria.where("text_entry").contains("No");
		Criteria c = Criteria.where("text_entry").contains("ea"); // like 검색 가능
//		Query q = new CriteriaQuery(c).setPageable(page);
		Query q = new CriteriaQuery(c).addSort(Sort.by(Direction.DESC , "doc_id"));
		
		SearchHitsIterator<ElasticDto3> stream = operations.searchForStream(q, ElasticDto3.class);
		
		List<ElasticDto3> sampleEntities = new ArrayList<>();
		
		while (stream.hasNext()) {
			SearchHit<ElasticDto3> ss= stream.next();
			sampleEntities.add(ss.getContent());
		}
		
		long count = stream.getTotalHits();
		System.out.println(count);
		
		sampleEntities.forEach(m -> {
			System.out.println(m.toString());
		});
	}
	
	public void findBy(int pageIndex, int pageSize) {
		// 전체
		List<ElasticDto3> list = logRepository2
				.findBy(PageRequest.of(0, 10, Sort.by(Direction.ASC , "doc_id")))
				.skip(10000).limit(10).collect(Collectors.toList());

//		List<ElasticDto3> list = logRepository2
//				.findBy(PageRequest.of(0, 25))
//				.skip(10000).collect(Collectors.toList());
		
		System.out.println(logRepository2.count());
		
		// 필드 검색
//		List<ElasticDto3> list = logRepository2.findByTextEntry("lige").skip(400).limit(10).collect(Collectors.toList());
//		long count = logRepository2.countByTextEntry("lige");
//		System.out.println(count);
		
		System.out.println(list.size());
		list.forEach(m -> {
			System.out.println(m);
		});		
	}
	
	public void findbyContaining() {
		List<ElasticDto3> list = logRepository2.findByTextEntryContaining("lige").skip(0).limit(10).collect(Collectors.toList());
		list.forEach(m -> {
			System.out.println(m);
		});
		System.out.println(logRepository2.countByTextEntryContaining("lige"));
	}
	
	public void sort() {
		//전체
//		List<ElasticDto3> list = logRepository2
//				.findBy(Sort.by(Direction.DESC , "doc_id"))
//				.skip(10000).limit(10).collect(Collectors.toList());
		
//		List<ElasticDto3> list = logRepository2
//				.findByTextEntry("lige", Sort.by(Direction.ASC , "doc_id"))
//				.skip(0)
//				.limit(10)
//				.collect(Collectors.toList());
		
//		List<ElasticDto3> list = logRepository2.findByTextEntry("NO", Sort.by(Direction.DESC , "doc_id")).collect(Collectors.toList());
		
		List<ElasticDto3> list = logRepository2.findByTextEntryOrderByDocIdDesc("NO").collect(Collectors.toList());
		long count = logRepository2.findByTextEntryOrderByDocIdDesc("NO").count();
		System.out.println(count);
		
		list.forEach(m -> {
			System.out.println(m);
		});
	}
	
	public void finaltest() {
		String orderBy = "DESC";
		
		List<ElasticDto3> list = logRepository2.findByTextEntryContaining("NO", Sort.by("ASC".equals(orderBy) ? Direction.ASC : Direction.DESC, "doc_id"))
//									.skip(0).limit(10)
									.collect(Collectors.toList());
		
		long count = logRepository2.countByTextEntryContaining("NO");
		System.out.println(count);
		
		AtomicInteger indexHolder = new AtomicInteger();
		list.forEach((m) -> {
			System.out.println(indexHolder.getAndIncrement() + "    " + m);
		});
		
	
		List<ElasticDto3> list2 = logRepository2.findBy(Sort.by(Direction.DESC , "doc_id")).skip(0).limit(10).collect(Collectors.toList());
		long count2 = logRepository2.count();
		System.out.println(count2);
		list2.forEach(m -> {
			System.out.println(m);
		});
//		
	}
}
