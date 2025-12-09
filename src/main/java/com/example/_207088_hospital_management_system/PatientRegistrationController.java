package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class PatientRegistrationController {
    public void handleRegister(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    public void handleCloseWindow(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }
    private void closeWindow(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
