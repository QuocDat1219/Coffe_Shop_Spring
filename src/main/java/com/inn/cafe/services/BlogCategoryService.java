package com.inn.cafe.services;

import com.inn.cafe.POJO.BlogCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BlogCategoryService {
    ResponseEntity<String> addNewBlogCategory(Map<String,String> requestMap);

    ResponseEntity<List<BlogCategory>> getAllBlogCategory(String fillterValue);

    ResponseEntity<String> updateBlogCategory(Map<String,String> requestMap);

    ResponseEntity<String> deleteBlogCategory(Integer id);
}
