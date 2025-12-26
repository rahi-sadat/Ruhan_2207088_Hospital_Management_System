package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DoctorloginController {
  @FXML
  private TextField txtDoctorId;
  @FXML private PasswordField txtDoctorPass;
    public void handleDoctorLogin(ActionEvent actionEvent) throws SQLException, IOException {
        if(!txtDoctorId.getText().isEmpty() && !txtDoctorPass.getText().isEmpty()){

            db database = new db();
            String DoctorId = txtDoctorId.getText();
            String password = txtDoctorPass.getText();

            String authenticatedId =database.authenticateDoctor(DoctorId,password);
            if(authenticatedId!=null){
                  FXMLLoader loader = new FXMLLoader((getClass().getResource("DoctorDashboard.fxml")));
                Parent root= loader.load();
                  Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Doctor Dashboard");
                stage.show();




            }

        }
        else
        {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK).show();
        }


    }
}
