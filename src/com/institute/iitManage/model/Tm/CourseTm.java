package com.institute.iitManage.model.Tm;

import javafx.scene.control.Button;

import java.util.Arrays;

public class CourseTm {
    private String courseID;
    private String name;
    private String[] subjects;
    private String teacherID;
    private double cost;
    private Button button;

    public CourseTm() {
    }

    public CourseTm(String courseID, String name, String[] subjects, String teacherID, double cost, Button button) {
        this.courseID = courseID;
        this.name = name;
        this.subjects = subjects;
        this.teacherID = teacherID;
        this.cost = cost;
        this.button = button;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "CourseTm{" +
                "courseID='" + courseID + '\'' +
                ", name='" + name + '\'' +
                ", subjects=" + Arrays.toString(subjects) +
                ", teacherID='" + teacherID + '\'' +
                ", cost=" + cost +
                ", button=" + button +
                '}';
    }

    public String getSubjectSaString (){
        String subject = null;
        for (String temp : subjects) {
            subject += ", " + temp;
        }
        return subject + "\b";
    }
}
