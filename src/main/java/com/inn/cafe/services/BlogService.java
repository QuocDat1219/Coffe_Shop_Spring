package com.inn.cafe.services;

import com.inn.cafe.POJO.Blog;
import com.inn.cafe.wrapper.BlogWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BlogService {
    ResponseEntity<String> addBlog(Map<String,String> requestMap);

    ResponseEntity<List<BlogWrapper>> getAllBlog();

    ResponseEntity<String> updateBlog(Map<String,String> requestMap);
}
