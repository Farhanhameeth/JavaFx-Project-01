package com.institute.iitManage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CodeVerificationFormController {
    public AnchorPane context;
    public Button btnVerify;
    public TextField txtEnterCode;
    public Label lblEmail;

    int code = 0;
    String selectEmail = "";

    public void verifyCodeOnAction(ActionEvent actionEvent) throws IOException {

        if (txtEnterCode.getText().equals(String.valueOf(code))) {
            //navigate reset password
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ResetPasswordForm.fxml"));
            Parent parent = fxmlLoader.load();
            ResetPasswordFormController controller = fxmlLoader.getController();
            controller.setUserData(selectEmail);
            Stage stage = (Stage) context.getScene().getWindow();
            stage.setScene(new Scene(parent));

        } else {
            new Alert(Alert.AlertType.ERROR,"Wrong Verification Code, Try Again").show();
        }
    }

    public void changeEmailOnAction() {
    }

    public void setUserDAta(int code, String email) {
        selectEmail = email;
        lblEmail.setText(email);
        this.code = code;
        System.out.println(email);
        System.out.println(code);
    }
}
