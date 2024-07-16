package com.institute.iitManage.controller;

import com.institute.iitManage.db.Database;
import com.institute.iitManage.model.User;
import com.institute.iitManage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpFormController {
    public AnchorPane context;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtPassword;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {

        String firstName = txtFirstName.getText().trim().toLowerCase();
        String lastName = txtLastName.getText().trim().toLowerCase();
        String email = txtEmail.getText().trim().toLowerCase();
        String password = txtPassword.getText().trim();

        /*Database.userTable.add(
                new User(firstName,lastName,email,new PasswordManager().encrypt(password))
        );*/
        User user = new User(firstName, lastName, email, new PasswordManager().encrypt(password));

        boolean isSaved = false;
        try {
            isSaved = signUp(user);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION,"Your Account has been Created.....!").show();
                setUI("LoginForm");
            } else {
                new Alert(Alert.AlertType.INFORMATION,"Something went wrong! Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void alreadyHaveAnAccountOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        setUI("LoginForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }

    private boolean signUp(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/iitmanage","root","root@123");

        String sql = "INSERT INTO user VALUES('"+ user.getEmail()+"','"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getPassword()+"')";

        Statement statement = connection.createStatement();

        /*int rowCount = statement.executeUpdate(sql);

        if (rowCount > 0) {
            return true;
        } else {
            return false;
        }*/

        //Same function as above in a shorter method.
        return statement.executeUpdate(sql) > 0;
    }
}
