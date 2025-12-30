package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public void handleUploadReports(ActionEvent actionEvent) throws SQLException {
        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Show the dialog
        File selectedFile = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            new Alert(Alert.AlertType.INFORMATION, "Report uploaded successfully " ).showAndWait();
            String filePath = selectedFile.getAbsolutePath();
            saveReportPathToDatabase(filePath);
        }
    }
    private void saveReportPathToDatabase(String path) throws SQLException {
        String patientId = UserSession.getUserId();
        String sql = "UPDATE patients SET report_path = ? WHERE patient_id = ?";

        db database = new db();
        Connection conn = database.getPatientConnection();

        if (conn == null) {
            System.err.println("Error: Database connection is null!");
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, path);
            pstmt.setString(2, patientId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Report path saved successfully!");
            } else {
                System.out.println("No patient found with ID: " + patientId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showBookAppointment(ActionEvent actionEvent) {
        loadView("BookAppointment.fxml");

    }

    public void showProfile(ActionEvent actionEvent) {
        loadView("PatientProfile.fxml");
    }
}
