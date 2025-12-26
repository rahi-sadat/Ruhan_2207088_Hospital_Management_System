package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;


public class AddDoctorFormController {
    private db database = new db();
    @FXML
    private TextField txtDoctorName, txtSpecialization, txtPhoneNumber, txtEmail,txtSchedule;
    public void handleSaveDoctor(ActionEvent actionEvent) {
        try {

            String name = txtDoctorName.getText();

            String spec = txtSpecialization.getText();
            String phone = txtPhoneNumber.getText();
            String email = txtEmail.getText();
           String schedule = txtSchedule.getText();
            if(name.isEmpty() || spec.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill name and specialization!").showAndWait();
                return;
            }


            // db te addDoctor e dicchi , save korar
            database.addDoctor(name, spec, phone, email,schedule);
            new Alert(Alert.AlertType.INFORMATION,  "Doctor added !").showAndWait();


            txtDoctorName.clear(); txtSpecialization.clear(); txtPhoneNumber.clear(); txtEmail.clear(); txtSpecialization.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGoBack(ActionEvent actionEvent) {
        StackPane centerPane = (StackPane) ((javafx.scene.Node) actionEvent.getSource()).getScene().lookup("#centerContentPane");
        centerPane.getChildren().clear();
    }
}
