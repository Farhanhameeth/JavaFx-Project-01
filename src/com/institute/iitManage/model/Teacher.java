package com.institute.iitManage.model;

public class Teacher {

    private String teacherID;
    private String name;
    private String address;
    private String contact;

    public Teacher() {
    }

    public Teacher(String teacherID, String name, String address, String contact) {
        this.teacherID = teacherID;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherID='" + teacherID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}