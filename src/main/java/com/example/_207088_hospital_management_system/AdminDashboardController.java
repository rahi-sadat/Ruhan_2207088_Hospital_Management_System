package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private StackPane centerContentPane;
    public void handleViewPatients(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("ViewPatient.fxml"));
        Parent root= fxmlLoader.load();
        centerContentPane.getChildren().setAll(root);
    }

    public void handleLogoutAdmin(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleShowAddDoctorForm(ActionEvent actionEvent) throws IOException {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddDoctorForm.fxml"));
            Parent root = loader.load();
            centerContentPane.getChildren().setAll(root);
        }
    @FXML
    private void handleViewDoctors(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDoctors.fxml"));
            Node view = loader.load();


            centerContentPane.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    @FXML
    public void handleShowApproveAppointments(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ApproveAppointments.fxml"));
        Parent root = loader.load();
        centerContentPane.getChildren().setAll(root);
    }
    }

