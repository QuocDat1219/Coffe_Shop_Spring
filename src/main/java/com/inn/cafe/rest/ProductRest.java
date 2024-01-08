package com.inn.cafe.rest;

import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/product")
public interface ProductRest {

    @PostMapping(path = "/create")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String,String> requestMap);

    @GetMapping(path = "/all")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String,String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PostMapping(path = "/update-status")
    ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap);

    @GetMapping(path = "/get-by-category/{id}")
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);

    @GetMapping(path = "/get-product/{id}")
    ResponseEntity<List<ProductWrapper>> getProductById(@PathVariable Integer id);

}
