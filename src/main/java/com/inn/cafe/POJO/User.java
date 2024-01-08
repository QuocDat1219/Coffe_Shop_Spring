package com.inn.cafe.POJO;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId",query = "select u from User u where u.email=:email")

@NamedQuery(name ="User.getAllUser", query = "SELECT new com.inn.cafe.wrapper.UserWrapper(u.id, u.name, u.email, u.contacNumber, u.status) FROM User u WHERE u.role = 'user'")

@NamedQuery(name = "User.updateStatus", query = "Update User u set u.status=:status where u.id =: id")

@NamedQuery(name ="User.getAllAdmin", query = "SELECT u.email FROM User u WHERE u.role = 'admin'")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private  String name;

    @Column(name = "contacNumber")
    private String contacNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    public User() {
    }
}
