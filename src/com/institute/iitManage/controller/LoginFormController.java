package com.institute.iitManage.controller;

import com.institute.iitManage.db.Database;
import com.institute.iitManage.model.User;
import com.institute.iitManage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtPassword;
    public Hyperlink txtforgotpassword;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        String email = txtEmail.getText().trim().toLowerCase();
        String password = txtPassword.getText().trim();

        //Check user in noob level
        /*for (User user : Database.userTable) {
            if (user.getEmail().equals(email)) {
                if (user.getPassword().equals(password)) {
                    System.out.println(user.toString());
                    return;
                } else {
                    new Alert(Alert.AlertType.ERROR,"Email or Password Incorrect").show();
                    return;
                }
            }
        }
        new Alert(Alert.AlertType.ERROR,"User Not Found....!");*/

        //Check user in pro level
        Optional<User> selectedUser = Database.userTable.stream().filter(e -> e.getEmail().equals(email)).findFirst();
        if (selectedUser.isPresent()) {
            if (new PasswordManager().checkPassword(password,selectedUser.get().getPassword())) {
                System.out.println(selectedUser.get());
                setUI("Dashboard");
            } else {
                new Alert(Alert.AlertType.ERROR,"Email or Password Incorrect....!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"User Not Found....!").show();
        }

    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("SignUpForm");
    }

    public void forgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUI("ForgotPasswordForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }
}
