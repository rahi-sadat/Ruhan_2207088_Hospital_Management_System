package com.example._207088_hospital_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.List;

public class ViewDoctorController {

    @FXML private TableView<Doctor> doctorTable;
    @FXML private TableColumn<Doctor, Integer> colId;
    @FXML private TableColumn<Doctor, String> colName;
    @FXML private TableColumn<Doctor, String> colSpecialization;
    @FXML private TableColumn<Doctor, String> colPhone;
    @FXML private TableColumn<Doctor, String> colEmail;
    @FXML private TableColumn<Doctor, String> colSchedule;

    private final db database = new db();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));

        loadDoctorData();
    }

    private void loadDoctorData() {
        try {

            List<Doctor> doctors = database.getAllDoctors();

            ObservableList<Doctor> observableList = FXCollections.observableArrayList(doctors);

            doctorTable.setItems(observableList);

        } catch (SQLException e) {
            System.err.println("Error fetching doctor data: " + e.getMessage());

        }
    }
    @FXML
    private void handleDeleteDoctor() {
           // doctor select korlam
        Doctor selectedDoc = doctorTable.getSelectionModel().getSelectedItem();


        if (selectedDoc == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a doctor to delete.").show();
            return;
        }

        try {
    // db te ei method ache
            new db().deleteDoctor(selectedDoc.getId());

           // table thake remove
            doctorTable.getItems().remove(selectedDoc);


            System.out.println("Doctor " + selectedDoc.getName() + " deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: Could not delete doctor.").show();
        }
    }
}
