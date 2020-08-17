package com.dlq.es.repository;

import com.dlq.es.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


/**
 *@program: SpringBoot_Advanced
 *@description:
 *@author: Hasee
 *@create: 2020-08-17 19:22
 */
public interface BookRepository extends ElasticsearchRepository<Book,Integer> {

    //
    public List<Book> findByBookNameLike(String bookName);
}
