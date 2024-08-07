package com.institute.iitManage.model;

import java.util.Date;

public class Intake {

    String id;
    String name;
    Date date;
    String course;
    boolean status;

    public Intake() {}

    public Intake(String id, String name, Date date, String course, boolean status) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.course = course;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
