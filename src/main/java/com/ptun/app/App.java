package com.ptun.app;/**
 * Created by Lenovo on 4/28/2017.
 */

import com.ptun.app.statics.Constants;
import com.ptun.app.statics.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.IOException;
import java.util.Scanner;

public class App extends Application {

    private static final String MAIN_UI = "/main.fxml";
    public static Stage PRIMARY_STAGE = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, DRException {
        this.PRIMARY_STAGE = primaryStage;
        DBUtil.migrateUp();
        DBUtil.initSettingData();
        Parent root = FXMLLoader.load(getClass().getResource(MAIN_UI));
        primaryStage.setTitle(Constants.APP_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
