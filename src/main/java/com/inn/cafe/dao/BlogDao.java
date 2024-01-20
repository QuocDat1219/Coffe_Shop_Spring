package com.inn.cafe.dao;

import com.inn.cafe.POJO.Blog;
import com.inn.cafe.wrapper.BlogWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogDao extends JpaRepository<Blog,Integer> {
    List<BlogWrapper> getAllBlog();
}
