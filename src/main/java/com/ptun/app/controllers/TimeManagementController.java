package com.ptun.app.controllers;

import com.j256.ormlite.dao.Dao;
import com.ptun.app.customui.NumberTextField;
import com.ptun.app.db.models.Time;
import com.ptun.app.db.models.TimeManagement;
import com.ptun.app.eventbus.EventBus;
import com.ptun.app.eventbus.events.ManagemenTimeEvent;
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
public class TimeManagementController implements Initializable {

    private Dao<TimeManagement, Integer> thisDao = TimeManagement.getDao();
    private TimeManagement timeManagement = thisDao.queryForId(Constants.SETTING_ID);

    @FXML
    private TextField tfOnDuty, tfOffDuty;
    @FXML
    private NumberTextField tfLateTolerance, tfEarlyTolerance;

    public TimeManagementController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData(this.timeManagement);
    }

    private void setData(TimeManagement timeManagement) {
        if (timeManagement != null) {
            tfOnDuty.setText(timeManagement.getOnDuty().getTime());
            tfOffDuty.setText(timeManagement.getOffDuty().getTime());
            tfLateTolerance.setText(timeManagement.getLateTolerance()+"");
            tfEarlyTolerance.setText(timeManagement.getEarlyTolerance()+"");
        }
    }

    @FXML
    private void saveSetting(ActionEvent actionEvent) {
        try {
            TimeManagement timeManagement = new TimeManagement();
            timeManagement.setId(1);
            timeManagement.setOnDuty(new Time(tfOnDuty.getText()));
            timeManagement.setOffDuty(new Time(tfOffDuty.getText()));
            timeManagement.setEarlyTolerance(Integer.parseInt(tfEarlyTolerance.getText()));
            timeManagement.setLateTolerance(Integer.parseInt(tfLateTolerance.getText().toString()));
            Dao.CreateOrUpdateStatus status = thisDao.createOrUpdate(timeManagement);
            String message = "";
            if (status.isUpdated()) {
                message = "diupdate";
            }else if (status.isCreated()) {
                message = "disimpan";
            }
            thisDao.refresh(timeManagement);
            setData(timeManagement);
            EventBus.getDefault().post(new ManagemenTimeEvent(timeManagement));
            Util.showNotif("Sukses", String.format("Pengaturan telah %s", message), NotificationType.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Util.showNotif("Ada kesalahan", "Cek kembali inputan anda!", NotificationType.ERROR);
        }
    }
}
