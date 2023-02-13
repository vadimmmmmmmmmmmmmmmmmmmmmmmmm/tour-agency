package com.razkuuuuuuu.touragency.entities.entity;

import java.io.Serializable;

public class TourLocalisationInfo implements Serializable {
    private static final long serialVersionUID = 273531650296868314L;
    private String locale;
    private String title;
    private String description;

    public TourLocalisationInfo() {
        locale="";
        title="";
        description="";
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
