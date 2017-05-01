package com.ptun.app.controllers;

import com.j256.ormlite.dao.Dao;
import com.ptun.app.apis.endpoints.EasyLinkPoints;
import com.ptun.app.customui.NumberTextField;
import com.ptun.app.db.models.User;
import com.ptun.app.enums.PEGAWAI_CHOICES;
import com.ptun.app.eventbus.EventBus;
import com.ptun.app.eventbus.events.NewUserEvent;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import lombok.Data;
import tray.notification.NotificationType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by khairulimam on 01/05/17.
 */
@Data
public class AddUserFromMachineController implements Initializable {

    @FXML
    private NumberTextField tfPin;
    @FXML
    private RadioButton rbHakim, rbStaff;

    private ToggleGroup rbJabatanGroup = new ToggleGroup();
    private Dao<User, Integer> dao = User.getDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rbHakim.setText(PEGAWAI_CHOICES.HAKIM.name());
        rbStaff.setText(PEGAWAI_CHOICES.STAFF.name());
        rbHakim.setToggleGroup(rbJabatanGroup);
        rbStaff.setToggleGroup(rbJabatanGroup);
    }

    private RadioButton getPegawai() {
        return (RadioButton) rbJabatanGroup.getSelectedToggle();
    }

    @FXML
    private void addUser(ActionEvent actionEvent) {
        try {
            int PIN = Integer.parseInt(tfPin.getText());
            User newUser = new User();
            newUser.setPIN(PIN);
            newUser.setJabatan(getPegawai().getText());
            dao.create(newUser);
            resetForm();
            EventBus.getDefault().post(new NewUserEvent(newUser));
            Util.showNotif("Sukses", String.format("Berhasil menambahkan %s baru", newUser.getJabatan()), NotificationType.SUCCESS);
        } catch (NullPointerException e) {
            Util.showNotif("Error", String.format("Ada kesalahan %s", e.getMessage()), NotificationType.ERROR);
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Util.showNotif("Error", String.format("Ada kesalahan %s", e.getMessage()), NotificationType.ERROR);
            e.printStackTrace();
        }
    }

    public void resetForm() {
        tfPin.setText("");
        getRbJabatanGroup().getSelectedToggle().setSelected(false);
    }

}
