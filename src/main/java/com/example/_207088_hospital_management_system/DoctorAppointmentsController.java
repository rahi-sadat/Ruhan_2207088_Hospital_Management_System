package com.example._207088_hospital_management_system;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.sql.SQLException;

public class DoctorAppointmentsController {
    @FXML private TableView<Appointment> tblAppointments;
    @FXML private TableColumn<Appointment, String> colName, colGender, colBlood, colHistory, colDate;
    @FXML private TableColumn<Appointment, Integer> colAge;
    @FXML private TableColumn<Appointment, Void> colReport;

    @FXML
    public void initialize() {

        colName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBlood.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        colHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

        setupReportPreview();
        refreshTable();
    }

    private void setupReportPreview() {
        colReport.setCellFactory(param -> new TableCell<>() {
            private final ImageView imgView = new ImageView();
            {
                imgView.setFitHeight(50);
                imgView.setFitWidth(50);
                imgView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Appointment app = getTableView().getItems().get(getIndex());
                    String path = app.getReportPath();
                    if (path != null && !path.isEmpty()) {
                        File file = new File(path);
                        if (file.exists()) {
                            imgView.setImage(new Image(file.toURI().toString()));
                            setGraphic(imgView);
                        } else {
                            setGraphic(new Label("No File"));
                        }
                    } else {
                        setGraphic(new Label("N/A"));
                    }
                }
            }
        });
    }

    @FXML
    private void handleMarkVisited() {
        Appointment selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select a patient from the table first.");
            return;
        }

        try {
            new db().updateAppointmentStatus(selected.getAppId(), "VISITED");    // visited mark kore dilam
            refreshTable(); // click korle disappaer hoye jabe
        } catch (SQLException e) {
            showError("Database error.");
        }
    }

    private void refreshTable() {
        try {
            db database = new db();
            tblAppointments.setItems(FXCollections.observableArrayList(
                    database.getDoctorAppointments(UserSession.getUserId())   // jader approved ache shudu tader
            ));
        } catch (SQLException e) {
            showError("Data load error: " + e.getMessage());
        }
    }

    private void showError(String msg) { new Alert(Alert.AlertType.ERROR, msg).show(); }
    private void showWarning(String msg) { new Alert(Alert.AlertType.WARNING, msg).show(); }
}