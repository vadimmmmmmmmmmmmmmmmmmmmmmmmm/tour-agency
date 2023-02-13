package com.razkuuuuuuu.touragency.entities.entity;

import java.io.Serializable;

public class Hotel implements Serializable {
    private static final long serialVersionUID = -1361620628214457937L;
    private int id;
    private int ranking;
    private String name;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return '('+ranking+','+name+')';
    }
}
