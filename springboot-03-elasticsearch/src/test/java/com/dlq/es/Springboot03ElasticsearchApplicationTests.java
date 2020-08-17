package com.dlq.es;

import com.dlq.es.bean.Article;
import com.dlq.es.bean.Book;
import com.dlq.es.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class Springboot03ElasticsearchApplicationTests {

	@Autowired
	JestClient jestClient;
	@Autowired
	BookRepository bookRepository;



	@Test
	public void testEsSpringData() {
//		Book book = new Book();
//		book.setId(1);
//		book.setBookName("西游记");
//		book.setAuthor("吴承恩");
//		bookRepository.index(book);

		for (Book book : bookRepository.findByBookNameLike("游")) {
			System.out.println(book);
		}
	}

	@Test
	void contextLoads() {
		//1、给Es中索引（保存）一个文档；
		Article article = new Article();
		article.setId(1);
		article.setTitle("好消息");
		article.setAuthor("zhangsan");
		article.setContent("Hello World");

		//构建一个索引功能
		Index index = new Index.Builder(article).index("dlqaaa").type("news").build();

		try {
			//执行
			jestClient.execute(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//测试搜索
	@Test
	public void testSearch() {
		//查询表达式
		String json ="{\n" +
				"    \"query\" : {\n" +
				"        \"match\" : {\n" +
				"            \"content\" : \"hello\"\n" +
				"        }\n" +
				"    }\n" +
				"}";
		//构建搜索功能
		Search search = new Search.Builder(json).addIndex("dlqaaa").addType("news").build();

		//执行
		try {
			SearchResult result = jestClient.execute(search);
			System.out.println(result.getJsonString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
