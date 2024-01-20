package com.inn.cafe.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQuery(name = "Blog.getAllBlog", query = "select new com.inn.cafe.wrapper.BlogWrapper(b.id, b.title,b.image,b.description,b.status,b.createdAt,b.updatedAt,b.blogCategory.id,b.blogCategory.name) from Blog b")
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Blog implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name="status")
    private String status;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blogcategory_fk", nullable = false)
    private BlogCategory blogCategory;
}
