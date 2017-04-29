package com.ptun.app.statics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    public static void showNotif(String title, String message, NotificationType type) {
        TrayNotification tray = new TrayNotification(title, message, type);
        tray.setAnimationType(AnimationType.POPUP);
        tray.showAndDismiss(Duration.seconds(1));
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

}
