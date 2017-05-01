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

    private Stage appSettingStage, timeManagementStage, addUserToMachineStage, addUserFromMachineStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appSettingStage = Util.makeDialogStage(getClass().getResource(Constants.APP_SETTING_UI), "Pengaturan Aplikasi", App.PRIMARY_STAGE);
        timeManagementStage = Util.makeDialogStage(getClass().getResource(Constants.MANAGEMEN_TIME_UI), "Managemen Waktu", App.PRIMARY_STAGE);
        addUserToMachineStage = Util.makeDialogStage(getClass().getResource(Constants.ADDUSERTOMACHINE_FXML), "Tambah Karyawan Ke Mesin", App.PRIMARY_STAGE);
        addUserFromMachineStage = Util.makeDialogStage(getClass().getResource(Constants.ADDUSERFROMMACHINE_FXML), "Tambah Karyawan Dari Mesin", App.PRIMARY_STAGE);
    }

    public void showFormAppSetting(ActionEvent actionEvent) {
        if (appSettingStage != null)
            appSettingStage.showAndWait();
    }

    public void showFormTimeManagement(ActionEvent actionEvent) {
        if (timeManagementStage != null)
            timeManagementStage.showAndWait();
    }

    public void showFormAddUserToMachine(ActionEvent actionEvent) {
        if (addUserToMachineStage != null)
            addUserToMachineStage.showAndWait();
    }

    public void showFormTambahKaryawanDariMesin(ActionEvent actionEvent) {
        if (addUserFromMachineStage != null)
            addUserFromMachineStage.showAndWait();
    }
}
