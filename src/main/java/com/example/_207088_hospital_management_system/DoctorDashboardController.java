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
import java.util.Objects;

public class DoctorDashboardController {
    @FXML private Label lblDoctorName;
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {

        lblDoctorName.setText("Welcome, Dr. " + UserSession.getUserName());
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void showProfile() { loadView("DoctorProfile.fxml"); }
    @FXML void showAppointments() { loadView("DoctorAppointments.fxml"); }
    @FXML void showSchedule() { loadView("DoctorSchedule.fxml"); }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        UserSession.cleanUserSession();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = fxmlLoader.load();
        Stage newStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
