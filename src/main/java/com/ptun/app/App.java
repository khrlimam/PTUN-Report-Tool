package com.ptun.app;

/**
 * Created by Lenovo on 4/28/2017.
 */

import com.ptun.app.controllers.LoginModalController;
import com.ptun.app.enums.PEGAWAI_CHOICES;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.IOException;
import java.util.Arrays;

import static com.ptun.app.statics.Constants.LOGIN_UI;
import static com.ptun.app.statics.Constants.MAIN_UI;

public class App extends Application {

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
        loginFirst();
    }

    void loginFirst() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(LOGIN_UI));
            AnchorPane pane = loader.load();
            LoginModalController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(PRIMARY_STAGE);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            final boolean[] isValid = {false};
            controller.getBtnLogin().setOnAction(event -> {
                isValid[0] = controller.isCredentialValid();
                if (isValid[0]) stage.close();
                else controller.getLblPrompt().setText("Login gagal!");
            });
            stage.setOnCloseRequest(event -> {
                if (!isValid[0])
                    System.exit(0);
            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
