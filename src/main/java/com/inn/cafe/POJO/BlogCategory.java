package com.inn.cafe.POJO;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="BlogCategory.getAllBlogCategory",query = "select c from BlogCategory c where c.id in (select p.blogCategory from Blog p where p.status ='true')")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "blogcategory")
public class BlogCategory implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
