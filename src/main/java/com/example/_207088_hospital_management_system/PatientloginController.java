package com.example._207088_hospital_management_system;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PatientloginController {
    @FXML
    private void handleCreateAccount(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatientRegistrationForm.fxml"));
            Parent root = fxmlLoader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Patient Registration");


            newStage.setScene(new Scene(root));
            newStage.show();

        } catch (Exception e) {
            System.err.println("Failed to open new window of registration form  " + e.getMessage());

        }
    }
    @FXML private TextField txtPatientId;
    @FXML private PasswordField txtPatientPass;


    public void handlePatientLogin(ActionEvent actionEvent) throws IOException, SQLException {
        if (!(txtPatientId.getText().isEmpty() || txtPatientPass.getText().isEmpty())) {
            db database = new db();
            String patientId = txtPatientId.getText();
            String password = txtPatientPass.getText();

            String authenticatedId = database.authenticatePatient(patientId, password);
            if (authenticatedId != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PatientDashboard.fxml"));
                Parent root = fxmlLoader.load();
                Stage newStage = new Stage();
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.setTitle("Patient Dashboard");
                newStage.show();
            } else {
                 new Alert(Alert.AlertType.ERROR, "Invalid Patient ID or Password").showAndWait();
            }


        }
    }
}
