package com.institute.iitManage.controller;

import com.institute.iitManage.util.tools.VerificationCodeGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class ForgotPasswordFormController {
    public AnchorPane context;
    public Button btnSaveTeacher;
    public TextField txtEmail;

    public void loginOnAction(ActionEvent actionEvent) {
    }

    public void sendVerificationOnAction(ActionEvent actionEvent) {
        int verificationCode = new VerificationCodeGenerator().getCode(5);

        try {
            String fromEmail = "farhanhameeth1@gmail.com";
            String toEmail = txtEmail.getText();

            String host = "localhost";
            Properties properties = System.getProperties();
            properties.setProperty("mail.smpt.host", host);

            Session session = Session.getDefaultInstance(properties);

            Message mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(fromEmail));
            mimeMessage.setSubject("Verification Code ");
            mimeMessage.setText("Your verification Code id :"+verificationCode);
            mimeMessage.addRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(toEmail));

            //Transport.send(mimeMessage);

            //System.out.println(verificationCode);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CodeVerificationForm.fxml"));
            Parent parent = fxmlLoader.load();
            CodeVerificationFormController controller = fxmlLoader.getController();
            controller.setUserDAta(verificationCode,txtEmail.getText());
            Stage stage = (Stage) context.getScene().getWindow();
            stage.setScene(new Scene(parent));

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        /*try {
            String fromEmail = "farhanhameeth1@gmail.com";
            String toEmail = txtEmail.getText();

            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, "your-app-password");
                }
            });

            Message mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(fromEmail));
            mimeMessage.setSubject("Verification Code");
            mimeMessage.setText("Your verification code is: " + verificationCode);
            mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();



        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }

}
