package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class PatientDashboardController {
    @FXML
    private   Label lblPatientName;
    @FXML private StackPane contentArea;
    public void initialize() {

        lblPatientName.setText("Welcome, " + UserSession.getUserName());
    }
    private void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlFile + ": " + e.getMessage());
        }
    }
    public void handleLogout(ActionEvent actionEvent) throws IOException {
        UserSession.cleanUserSession();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = fxmlLoader.load();

        Stage newStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();


        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void showReports(ActionEvent actionEvent) {
        loadView("PatientReports.fxml");
    }

    public void showBookAppointment(ActionEvent actionEvent) {
        loadView("BookAppointment.fxml");

    }

    public void showProfile(ActionEvent actionEvent) {
        loadView("PatientProfile.fxml");
    }
}
