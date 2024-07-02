package com.institute.iitManage.db;

import com.institute.iitManage.model.Course;
import com.institute.iitManage.model.Student;
import com.institute.iitManage.model.Teacher;
import com.institute.iitManage.model.User;
import com.institute.iitManage.util.security.PasswordManager;

import java.util.ArrayList;

public class Database {

    public static ArrayList<User> userTable = new ArrayList();
    public static ArrayList<Student> studentTable = new ArrayList();
    public static ArrayList<Teacher> teacherTable = new ArrayList();
    public static ArrayList<Course> courseTable = new ArrayList();

    static {
        userTable.add(new User(
                "Farhan",
                "Hameeth",
                "f",
                new PasswordManager().encrypt("1")
        ));
    }
}
