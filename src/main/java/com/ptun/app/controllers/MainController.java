package com.ptun.app.controllers;

import com.ptun.app.App;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Lenovo on 4/29/2017.
 */
public class MainController implements Initializable {

    private Stage appSettingStage, timeManagementStage, addUserStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appSettingStage = Util.makeDialogStage(getClass().getResource(Constants.APP_SETTING_UI), "Pengaturan Aplikasi", App.PRIMARY_STAGE);
        timeManagementStage = Util.makeDialogStage(getClass().getResource(Constants.MANAGEMEN_TIME_UI), "Managemen Waktu", App.PRIMARY_STAGE);
        addUserStage = Util.makeDialogStage(getClass().getResource(Constants.ADD_USER_UI), "Tambah Karyawan", App.PRIMARY_STAGE);
    }

    public void showFormAppSetting(ActionEvent actionEvent) {
        if (appSettingStage != null)
            appSettingStage.showAndWait();
    }

    public void showFormTimeManagement(ActionEvent actionEvent) {
        if (timeManagementStage != null)
            timeManagementStage.showAndWait();
    }

    public void showFormAddUser(ActionEvent actionEvent) {
        if (addUserStage != null)
            addUserStage.showAndWait();
    }
}
