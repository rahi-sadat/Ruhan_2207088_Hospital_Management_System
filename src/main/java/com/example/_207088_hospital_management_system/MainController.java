package com.example._207088_hospital_management_system;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {


    @FXML
    private StackPane contentStack;


    private final Map<String, Node> loginPanels = new HashMap<>();

    @FXML
    public void initialize() {
        // first e load kora lagbe
        loadAllLoginPanels();
       // eita default
        showPanel("Patient");
    }


    private void loadAllLoginPanels() {
        try {
    //load kori
            Node patientPanel = FXMLLoader.load(getClass().getResource("Patientlogin.fxml"));
            Node doctorPanel = FXMLLoader.load(getClass().getResource("Doctorlogin.fxml"));
            Node adminPanel = FXMLLoader.load(getClass().getResource("Adminlogin.fxml"));

           // map e store
            loginPanels.put("Patient", patientPanel);
            loginPanels.put("Doctor", doctorPanel);
            loginPanels.put("Admin", adminPanel);

            // stack e layer
            contentStack.getChildren().addAll(patientPanel, doctorPanel, adminPanel);

        } catch (IOException e) {
            System.err.println("Error loading login FXML panels: " + e.getMessage());
            e.printStackTrace();

        }
    }

  //switching
    private void showPanel(String panelKey) {
      // iterate
        loginPanels.forEach((key, panel) -> {
            //  panelkey er shate check dibe
            boolean isVisible = key.equals(panelKey);  //true hoile
            panel.setVisible(isVisible);   //  visible  hobe

            panel.setManaged(isVisible);   // naile hobe na
        });
    }

   // button click e je function call hobe  

    @FXML
    private void handlePatientClick(ActionEvent event) {
        showPanel("Patient");
    }

    @FXML
    private void handleDoctorClick(ActionEvent event) {
        showPanel("Doctor");
    }

    @FXML
    private void handleAdminClick(ActionEvent event) {
        showPanel("Admin");
    }
}
