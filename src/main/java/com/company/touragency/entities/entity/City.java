package com.razkuuuuuuu.touragency.entities.entity;

import java.io.Serializable;

public class City implements Serializable {
    private static final long serialVersionUID = -8992501346143865453L;
    private int id;
    private String name;

    public City() {
        id = 0;
        name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
