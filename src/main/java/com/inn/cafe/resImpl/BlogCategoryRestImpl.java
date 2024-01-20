package com.inn.cafe.resImpl;

import com.inn.cafe.POJO.BlogCategory;
import com.inn.cafe.constents.CafeConstans;
import com.inn.cafe.rest.BlogCategoryRest;
import com.inn.cafe.services.BlogCategoryService;
import com.inn.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class BlogCategoryRestImpl implements BlogCategoryRest {

    @Autowired
    BlogCategoryService blogCategoryService;

    @Override
    public ResponseEntity<String> addNewBlogCategory(Map<String, String> requestMap) {
        try{
            return blogCategoryService.addNewBlogCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<BlogCategory>> getAllBlogCategory(String fillterValue) {
        try {
            return blogCategoryService.getAllBlogCategory(fillterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateBlogCategory(Map<String, String> requestMap) {
        try {
            return blogCategoryService.updateBlogCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteBlogCategory(Integer id) {
        try {
            return blogCategoryService.deleteBlogCategory(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
