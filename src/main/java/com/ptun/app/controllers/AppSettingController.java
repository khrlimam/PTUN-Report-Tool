package com.ptun.app.controllers;

import com.j256.ormlite.dao.Dao;
import com.ptun.app.customui.NumberTextField;
import com.ptun.app.db.models.AppSettings;
import com.ptun.app.eventbus.EventBus;
import com.ptun.app.eventbus.events.AppSettingEvent;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import tray.notification.NotificationType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Lenovo on 4/29/2017.
 */
public class  AppSettingController implements Initializable {

    @FXML
    private TextField tfUsername, tfPassword, tfIpServer;

    @FXML
    private NumberTextField tfPortServer;

    private Dao<AppSettings, Integer> appSettingDao = AppSettings.getDao();
    private AppSettings appSettings = appSettingDao.queryForId(Constants.SETTING_ID);

    public AppSettingController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData(this.appSettings);
    }

    private void setData(AppSettings appSettings) {
        if (appSettings != null) {
            tfUsername.setText(appSettings.getUsername());
            tfPassword.setText(appSettings.getPassword());
            tfIpServer.setText(appSettings.getIpServer());
            tfPortServer.setText(appSettings.getPortServer());
        }
    }

    @FXML
    private void saveSetting(ActionEvent actionEvent) {
        try {
            AppSettings appSettings = new AppSettings();
            appSettings.setId(1);
            appSettings.setUsername(tfUsername.getText().toString());
            appSettings.setPassword(tfPassword.getText().toString());
            appSettings.setIpServer(tfIpServer.getText().toString());
            appSettings.setPortServer(tfPortServer.getText().toString());
            Dao.CreateOrUpdateStatus status = AppSettings.getDao().createOrUpdate(appSettings);
            String message = "";
            if (status.isUpdated()) {
                message = "diupdate";
            } else if (status.isCreated()) {
                message = "disimpan";
            }
            appSettingDao.refresh(appSettings);
            setData(appSettings);
            EventBus.getDefault().post(new AppSettingEvent(appSettings));
            Util.showNotif("Sukses", String.format("Pengaturan telah %s", message), NotificationType.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Util.showNotif("Ada kesalahan", "Cek kembali inputan anda!", NotificationType.ERROR);
        }
    }
}
