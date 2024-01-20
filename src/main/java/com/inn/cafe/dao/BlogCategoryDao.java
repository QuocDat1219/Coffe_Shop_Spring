package com.inn.cafe.dao;

import com.inn.cafe.POJO.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCategoryDao extends JpaRepository<BlogCategory,Integer> {
    List<BlogCategory> getAllBlogCategory();
}
