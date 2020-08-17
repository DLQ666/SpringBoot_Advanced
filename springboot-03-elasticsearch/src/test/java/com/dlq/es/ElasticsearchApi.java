package com.dlq.es;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

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
        CreateIndexRequest request = new CreateIndexRequest("dlqccc");
        //2、客户端执行请求,IndicesClient,请求后获得响应
        restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
    }
}
