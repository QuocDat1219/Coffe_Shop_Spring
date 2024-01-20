package com.inn.cafe.wrapper;

import com.inn.cafe.POJO.BlogCategory;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class BlogWrapper {

     Integer id;

     String title;

     String image;

     String description;

     String status;

     Date createdAt;

     Date updatedAt;
     Integer blogCategoryId;

     String blogCategoryName;

    public BlogWrapper(Integer id, String title, String image, String description, String status, Date createdAt, Date updatedAt, Integer blogCategoryId, String blogCategoryName) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.blogCategoryId = blogCategoryId;
        this.blogCategoryName = blogCategoryName;
    }

    public BlogWrapper(){

    }
}
