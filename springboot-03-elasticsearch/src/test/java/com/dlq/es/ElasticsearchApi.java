package com.dlq.es;

import com.alibaba.fastjson.JSON;
import com.dlq.es.bean.Article;
import io.searchbox.core.Search;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-17 20:43
 */
@SpringBootTest
public class ElasticsearchApi {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    //测试索引的创建
    @Test
    public void testCreateIndex() throws IOException {
        //1、创建索引请求对象
        CreateIndexRequest request = new CreateIndexRequest("dlqddd");
        //2、客户端执行请求,IndicesClient,请求后获得响应
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

    }

    //测试获取索引，判断是否存在
    @Test
    public void testExistIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("dlqddd");
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //测试删除索引
    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("dlqccc");
        //删除
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }
    
    //添加文档
    @Test
    public void testCreateDoc() throws IOException {
        Article article = new Article();
        article.setId(12);
        article.setAuthor("曹雪芹");
        article.setTitle("红楼梦");
        article.setContent("hahahhahahaa");
        //创建索引对象
        IndexRequest indexRequest = new IndexRequest("dlqbbb","book");
        //将我们的数据放入请求 json
        indexRequest.source(JSON.toJSONString(article), XContentType.JSON);
        //客户端发送请求，获取响应的结果
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.toString());
        System.out.println(index.status()); //对应我们命令返回的状态
    }
    
    //判断是否存在
    @Test
    public void testDocIsExists() throws IOException {
        GetRequest getRequest = new GetRequest("dlqbbb","book","1");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档
    @Test
    public void testGetDoc() throws IOException {
        GetRequest getRequest = new GetRequest("dlqbbb","book","1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());//打印文档内容
        System.out.println(getResponse);//返回的全部内容和命令是一样的
    }
    
    //更新文档信息
    @Test
    public void test() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("dlqbbb","book","1");
        Article article = new Article();
        article.setId(12);
        article.setAuthor("曹雪芹");
        article.setTitle("红楼梦");
        article.setContent("hahahhahahaa");
        updateRequest.doc(JSON.toJSONString(article),XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    //删除文档
    @Test
    public void testDeleteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("dlqbbb","book","1");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }

    //批量插入数据
    @Test
    public void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        ArrayList<Article> list = new ArrayList<>();
        list.add(new Article(1,"zhangsan","九阳神功","aaaaaaaaaaa"));
        list.add(new Article(2,"lisi","九阴真经","bbbbbbbbbbb"));
        list.add(new Article(3,"zhaoyi","葵花宝典","cccccccccc"));
        list.add(new Article(4,"huliu","五毒神功","ddddddddddd"));
        list.add(new Article(5,"sunsan","太极真经","eeeeeeeeeee"));
        //批处理请求
        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(new IndexRequest("dlqbbb","book")
                    .id(""+(i+1))
                    .source(JSON.toJSONString(list.get(i)),XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
    }

    //查询
    @Test
    public void testSearchRequest() throws IOException {
        //搜索请求
        SearchRequest searchRequest = new SearchRequest("dlqbbb");
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询条件，我们可以用 QueryBuilders 工具类来实现
        // QueryBuilders.termQuery 精确匹配
        // QueryBuilders.matchAllQuery() 匹配所有
        // sourceBuilder.highlighter() 高亮查询

        //没配置ik分词器查询“曹雪芹”查不到
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("author","曹");
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(search.getHits()));
        System.out.println("-----------------------------------");
        for (SearchHit searchHit : search.getHits().getHits()) {
            System.out.println(searchHit.getSourceAsMap());
        }
    }
}
