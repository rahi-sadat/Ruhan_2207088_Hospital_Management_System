package com.example._207088_hospital_management_system;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.sql.SQLException;

public class PatientProfileController {

    @FXML private Label lblId, lblName, lblPhone, lblAgeGender, lblBlood;
    @FXML private TextArea txtHistory;

    private final db database = new db();

    @FXML
    public void initialize() {
        String currentId = UserSession.getUserId();

        try {

            Patient p = database.getPatientById(currentId);

            if (p != null) {
                lblId.setText(p.getPatientId());
                lblName.setText(p.getName());
                lblPhone.setText(p.getPhone());
                lblAgeGender.setText(p.getAge() + " / " + p.getGender());
                lblBlood.setText(p.getBloodGroup());
                txtHistory.setText(p.getHistory());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}