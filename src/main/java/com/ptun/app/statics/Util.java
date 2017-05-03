package com.ptun.app.statics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Lenovo on 4/29/2017.
 */
public class Util {
    public static Stage makeDialogStage(URL FXMLLocation, String title, Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLLocation);
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            return stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DateTime getDateFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN);
        DateTime dt = formatter.parseDateTime(date);
        return dt;
    }

    public static TrayNotification setUpNotif(String title, String message, NotificationType type) {
        TrayNotification tray = new TrayNotification(title, message, type);
        tray.setAnimationType(AnimationType.POPUP);
        return tray;
    }

    public static void showNotif(String title, String message, NotificationType type) {
        TrayNotification tray = setUpNotif(title, message, type);
        tray.showAndDismiss(Duration.seconds(2));
    }

    public static String getTimeLaps(DateTime start, DateTime end) {
        long different = end.toDate().getTime() - start.toDate().getTime();
        long elapsedDays = different / Constants.daysInMilli;
        different = different % Constants.daysInMilli;

        long elapsedHours = different / Constants.hoursInMilli;
        different = different % Constants.hoursInMilli;

        long elapsedMinutes = different / Constants.minutesInMilli;
        different = different % Constants.minutesInMilli;

        long elapsedSeconds = different / Constants.secondsInMilli;

        elapsedHours = (elapsedHours < 0) ? 0 : elapsedHours;
        elapsedMinutes = (elapsedMinutes < 0) ? 0 : elapsedMinutes;

        return String.format("%d:%d", elapsedHours, elapsedMinutes);
    }

    public static Alert setUpDialog(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static Alert deleteConfirmation() {
        return setUpDialog("Konfirmasi", "Yakin ingin menghapus data?", "Hati-hati dengan pilihan anda!", Alert.AlertType.CONFIRMATION);
    }

    public static String getTemplateForPin(int pin) {
        return String.format("[{\"pin\": \"%d\",\"idx\": 0,\"alg_ver\": 39,\"template\": \"\"}]", pin);
    }

}
