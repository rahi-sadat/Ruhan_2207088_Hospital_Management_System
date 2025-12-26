package com.example._207088_hospital_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class ViewPatientController {


    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> colId;
    @FXML private TableColumn<Patient, String> colName;
    @FXML private TableColumn<Patient, String> colPhone;
    @FXML private TableColumn<Patient, Integer> colAge;
    @FXML private TableColumn<Patient, String> colGender;
    @FXML private TableColumn<Patient, String> colBlood;
    @FXML private TableColumn<Patient, String> colHistory;

    private final db database = new db();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBlood.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        colHistory.setCellValueFactory(new PropertyValueFactory<>("history"));


        loadPatientData();
    }

    private void loadPatientData() {
        try {
          // db thake j patientList ta pailam oita return  koche
            List<Patient> patients = database.getAllPatients();


            ObservableList<Patient> observableList = FXCollections.observableArrayList(patients);


            patientTable.setItems(observableList);

        } catch (SQLException e) {
            System.err.println("Error fetching patient data: " + e.getMessage());

            patientTable.setPlaceholder(new javafx.scene.control.Label("Could not load data. Database error."));
        }
    }
}
