package com.example._207088_hospital_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.SQLException;

public class BookAppointmentController {
    @FXML private TableView<Doctor> tblDoctors;
    @FXML private TableColumn<Doctor, String> colName, colSpec, colSchedule;
    @FXML private DatePicker datePicker;

    private final db database = new db();

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSpec.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

        loadDoctors();
    }

    private void loadDoctors() {
        try {
            // Assume you have a method in db.java that returns all doctors
            ObservableList<Doctor> doctorList = FXCollections.observableArrayList(database.getAllDoctors());
            tblDoctors.setItems(doctorList);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @FXML
    void handleRequestAppointment() {
        Doctor selectedDoc = tblDoctors.getSelectionModel().getSelectedItem();
        if (selectedDoc == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a doctor first!").show();
            return;
        }

        if (datePicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an appointment date!").show();
            return;
        }
        try {
            String patientId = UserSession.getUserId();
            String selectedDate = datePicker.getValue().toString();
            database.saveAppointmentRequest(patientId, String.valueOf(selectedDoc.getId()),selectedDate);
            new Alert(Alert.AlertType.INFORMATION, "Request sent to Admin for approval!").show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

