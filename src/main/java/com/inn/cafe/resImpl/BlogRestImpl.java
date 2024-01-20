package com.inn.cafe.resImpl;

import com.inn.cafe.POJO.Blog;
import com.inn.cafe.constents.CafeConstans;
import com.inn.cafe.rest.BlogRest;
import com.inn.cafe.services.BlogService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.BlogWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BlogRestImpl implements BlogRest {
    @Autowired
    BlogService blogService;

    @Override
    public ResponseEntity<String> addBlog(Map<String, String> requestMap) {
        try {
            return blogService.addBlog(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<BlogWrapper>> getAllBlog() {
        try {
           return blogService.getAllBlog();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateBlog(Map<String, String> requestMap) {
        try {
            return blogService.updateBlog(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
