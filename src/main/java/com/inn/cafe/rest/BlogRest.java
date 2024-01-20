package com.inn.cafe.rest;

import com.inn.cafe.POJO.Blog;
import com.inn.cafe.wrapper.BlogWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/blog")
public interface BlogRest {
    @PostMapping(path = "/create")
    ResponseEntity<String> addBlog(@RequestBody() Map<String,String> requestMap);

    @GetMapping(path = "/all")
    ResponseEntity<List<BlogWrapper>> getAllBlog();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateBlog(@RequestBody() Map<String,String> requestMap);
}
