package com.ptun.app.controllers;

import com.j256.ormlite.dao.Dao;
import com.ptun.app.db.models.AppSettings;
import com.ptun.app.statics.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Data;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Lenovo on 4/30/2017.
 */
@Data
public class LoginModalController implements Initializable {

    private Dao<AppSettings, Integer> loginDao = AppSettings.getDao();
    private AppSettings appSettings = loginDao.queryForId(Constants.SETTING_ID);

    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;

    @FXML
    private Label lblPrompt;

    @FXML
    private Button btnLogin;

    public LoginModalController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean isCredentialValid() {
        return appSettings.getUsername().equals(getTfUsername().getText()) && appSettings.getPassword().equals(getTfPassword().getText());
    }

}
