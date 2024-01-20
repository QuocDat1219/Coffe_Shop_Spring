package com.inn.cafe.servicesImpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Blog;
import com.inn.cafe.POJO.BlogCategory;
import com.inn.cafe.constents.CafeConstans;
import com.inn.cafe.dao.BlogDao;
import com.inn.cafe.services.BlogService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.BlogWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogDao blogDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addBlog(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){

                if(validateBlogMap(requestMap,false)){
                    blogDao.save(getBlogFromMap(requestMap,false));
                    return CafeUtils.getResponseEntity("Blog Added Successfully!",HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstans.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else{
                return CafeUtils.getResponseEntity(CafeConstans.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateBlogMap(Map<String, String> requestMap, boolean validatedId) {
        if(requestMap.containsKey("title")){
            if(requestMap.containsKey("id") && validatedId){
                return true;
            }else if(!validatedId){
                return true;
            }
        }
        return false;
    }

    private Blog getBlogFromMap(Map<String, String> requestMap, boolean isAdd) {
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setId(Integer.parseInt(requestMap.get("blogCategoryId")));

        Blog blog = new Blog();
        if(isAdd){
            blog.setId(Integer.parseInt(requestMap.get("id")));
            blog.setUpdatedAt(new Date(System.currentTimeMillis()));
        }else{
            blog.setStatus("true");
            blog.setCreatedAt(new Date(System.currentTimeMillis()));
        }
        blog.setBlogCategory(blogCategory);
        blog.setTitle(requestMap.get("title"));
        blog.setImage(requestMap.get("image"));
        blog.setDescription(requestMap.get("description"));
        return blog;
    }

    @Override
    public ResponseEntity<List<BlogWrapper>> getAllBlog() {
        try{
            return new ResponseEntity<>(blogDao.getAllBlog(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateBlog(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                if(validateBlogMap(requestMap,true)){
                    Optional<Blog> optional = blogDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        Blog blog = getBlogFromMap(requestMap,true);
                        blog.setStatus(optional.get().getStatus());
                        blogDao.save(blog);
                        return CafeUtils.getResponseEntity("Blog Updated Successfully",HttpStatus.OK);

                    }else {
                        return CafeUtils.getResponseEntity("Blog Does't Exists!",HttpStatus.OK);
                    }
                }
                return CafeUtils.getResponseEntity(CafeConstans.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else {
                return CafeUtils.getResponseEntity(CafeConstans.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstans.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
