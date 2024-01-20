package com.inn.cafe.servicesImpl;

import com.google.common.base.Strings;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.BlogCategory;
import com.inn.cafe.constents.CafeConstans;
import com.inn.cafe.dao.BlogCategoryDao;
import com.inn.cafe.services.BlogCategoryService;
import com.inn.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class BlogCategorySericeImpl implements BlogCategoryService {
    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BlogCategoryDao blogCategoryDao;
    @Override
    public ResponseEntity<String> addNewBlogCategory(Map<String, String> requestMap) {
        log.info("Blog category{}",requestMap);

        try{
            if(jwtFilter.isAdmin()){
                if(validateBlogCategoryMap(requestMap,false)){
                    blogCategoryDao.save(getBlogCategoryFromMap(requestMap,false));
                    return CafeUtils.getResponseEntity("Blog Category Added Successfully!",HttpStatus.OK);
                }
            }else {
                return CafeUtils.getResponseEntity(CafeConstans.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateBlogCategoryMap(Map<String, String> requestMap, boolean validatedId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validatedId){
                return true;
            }else if(!validatedId){
                return true;
            }
        }
        return false;
    }

    private BlogCategory getBlogCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        BlogCategory blogCategory = new BlogCategory();
        if(isAdd){
            blogCategory.setId(Integer.parseInt(requestMap.get("id")));
        }
        blogCategory.setName(requestMap.get("name"));
        return blogCategory;
    }

    @Override
    public ResponseEntity<List<BlogCategory>> getAllBlogCategory(String fillterValue) {
        try{
            if(!Strings.isNullOrEmpty(fillterValue) && fillterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<BlogCategory>>(blogCategoryDao.getAllBlogCategory(),HttpStatus.OK);
            }else {
                return new ResponseEntity<List<BlogCategory>>(blogCategoryDao.findAll(),HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateBlogCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateBlogCategoryMap(requestMap, true)) {
                    Optional optional = blogCategoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        blogCategoryDao.save(getBlogCategoryFromMap(requestMap, true));
                        return CafeUtils.getResponseEntity("Blog Category Updated Successfully!", HttpStatus.OK);
                    } else {
                        return CafeUtils.getResponseEntity("Blog Category Does't Exitst!", HttpStatus.OK);
                    }
                } else {
                    return CafeUtils.getResponseEntity(CafeConstans.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return CafeUtils.getResponseEntity(CafeConstans.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteBlogCategory(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional = blogCategoryDao.findById(id);
                if(!optional.isEmpty()){
                    blogCategoryDao.deleteById(id);
                    return CafeUtils.getResponseEntity("Blog Category Deteleted SuccessFully!",HttpStatus.OK);
                }else{
                    return CafeUtils.getResponseEntity("Blog Category Does't Exists",HttpStatus.OK);
                }
            }else {
                return CafeUtils.getResponseEntity(CafeConstans.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}