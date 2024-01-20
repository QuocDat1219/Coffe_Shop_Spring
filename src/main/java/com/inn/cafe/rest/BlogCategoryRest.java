package com.inn.cafe.rest;

import com.inn.cafe.POJO.BlogCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/blog-category")
public interface BlogCategoryRest{
    @PostMapping(path = "/create")
    ResponseEntity<String> addNewBlogCategory(@RequestBody() Map<String,String> requestMap);

    @GetMapping(path = "/all")
    ResponseEntity<List<BlogCategory>> getAllBlogCategory(@RequestParam(required = false) String fillterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateBlogCategory(@RequestBody() Map<String,String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteBlogCategory(@PathVariable Integer id);

}
