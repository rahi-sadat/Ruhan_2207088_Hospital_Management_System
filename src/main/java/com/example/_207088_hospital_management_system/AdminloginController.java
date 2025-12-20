package com.example._207088_hospital_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class AdminloginController {
    @FXML private TextField txtAdminId;
    @FXML private PasswordField txtAdminPass;
               String Id="admin";
               String Password="admin";
   public void handleOpenDashboard( ActionEvent event) throws IOException {      String AdminId=txtAdminId.getText();
            String AdminPassword=txtAdminPass.getText();
          if(AdminId.equals(Id) && AdminPassword.equals(Password))    // admin admin mille login
          {
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
              Parent root=loader.load();
              Scene scene = new Scene(root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();


          }
          else
          {
              new Alert(Alert.AlertType.ERROR,  "Invalid Admin ID or Password.").showAndWait();
          }


    }



}
