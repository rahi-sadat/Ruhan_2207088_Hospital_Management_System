package com.example._207088_hospital_management_system;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            e.printStackTrace();
        }
    }
}
