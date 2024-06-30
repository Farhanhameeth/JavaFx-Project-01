package com.institute.iitManage.model;

public class Course {

    private String courseID;
    private String courseName;
    private String[] subjects;
    private String teacherID;

    public Course() {
    }

    public Course(String courseID, String courseName, String[] subjects, String teacherID, double cost) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.subjects = subjects;
        this.teacherID = teacherID;
        this.cost = cost;
    }

    private double cost;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
}
