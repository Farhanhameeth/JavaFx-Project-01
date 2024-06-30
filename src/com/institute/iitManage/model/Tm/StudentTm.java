package com.institute.iitManage.model.Tm;

import javafx.scene.control.Button;

public class StudentTm {

    private String id;
    private String name;
    private String dob;
    private String address;
    private Button button;

    public StudentTm() {
    }

    public StudentTm(String id, String name, String dob, String address, Button button) {
        this.setId(id);
        this.setName(name);
        this.setDob(dob);
        this.setAddress(address);
        this.setButton(button);
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
