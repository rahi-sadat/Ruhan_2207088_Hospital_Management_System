package com.example._207088_hospital_management_system;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class ApproveAppointmentsController {
    @FXML private TableView<Appointment> tblPending;
    @FXML private TableColumn<Appointment, String> colPatientId, colDoctorId, colDate, colStatus;

    private db database = new db();

    @FXML
    public void initialize() {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadPendingAppointments();
    }

    private void loadPendingAppointments() {
        try {

            tblPending.setItems(FXCollections.observableArrayList(database.getAppointmentsByStatus("PENDING")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleApproveAction() {
        Appointment selected = tblPending.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an appointment to approve.").show();
            return;
        }

        try {
            database.updateAppointmentStatus(selected.getAppId(), "APPROVED");
            loadPendingAppointments();   // approve howar por abar load
                                        // karon only pending rakhbo  
            new Alert(Alert.AlertType.INFORMATION, "Appointment Approved Successfully!").show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
