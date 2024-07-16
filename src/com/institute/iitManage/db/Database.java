package com.institute.iitManage.db;

import com.institute.iitManage.model.*;
import com.institute.iitManage.model.Tm.IntakeTm;
import com.institute.iitManage.util.security.PasswordManager;

import java.awt.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Database {

    public static ArrayList<User> userTable = new ArrayList();
    public static ArrayList<Student> studentTable = new ArrayList();
    public static ArrayList<Teacher> teacherTable = new ArrayList();
    public static ArrayList<Course> courseTable = new ArrayList();
    public static ArrayList<Intake> intakeTable = new ArrayList();

    static {
        userTable.add(new User(
                "Farhan",
                "Hameeth",
                "f",
                new PasswordManager().encrypt("1")
        ));

        intakeTable.add(new Intake(
                "I-1",
                "Summar",
                new Date(),
                "SE",
                false
                ));

    }
}
