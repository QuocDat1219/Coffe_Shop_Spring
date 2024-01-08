package com.inn.cafe.rest;

import com.inn.cafe.POJO.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/category")
public interface CategoryRest {
    @PostMapping(path = "/create")
    ResponseEntity<String> addNewCategory(@RequestBody() Map<String,String> requestMap);

    @GetMapping(path = "/all")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateCategory(@RequestBody() Map<String,String> requestMap);
}
