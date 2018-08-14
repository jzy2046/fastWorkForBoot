package com.example.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "user_info_t")
public class User {
    /** 主键*/
    @Id
    @Column(name="id")
    private Integer id;

    /** 用户名*/
    @Column(name="user_name")
    private String userName;

    /** 密码*/
    @Column(name="password")
    private String password;

    /** 年龄*/
    @Column(name="age")
    private Integer age;
}