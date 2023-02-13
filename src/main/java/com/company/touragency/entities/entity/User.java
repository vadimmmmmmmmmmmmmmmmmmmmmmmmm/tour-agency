package com.razkuuuuuuu.touragency.entities.entity;


import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -5193866580150583385L;
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Integer status;
    private String language;

    public User() {
        id=0;
        email="";
        password="";
        name="";
        surname="";
        status=1;
        language="";
    }
    public User(Integer id, String email, String password, String name, String surname, Integer status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
