package com.ptun.app.controllers;

import com.google.gson.reflect.TypeToken;
import com.ptun.app.apis.GsonConverter;
import com.ptun.app.apis.RetrofitBuilder;
import com.ptun.app.apis.enpoints.EasyLinkPoints;
import com.ptun.app.apis.enpoints.models.AllScanLogs;
import com.ptun.app.apis.enpoints.models.AllUsers;
import com.ptun.app.apis.enpoints.models.Scan;
import com.ptun.app.apis.enpoints.models.User;
import com.ptun.app.controllers.dataoperations.DataScanLogOperations;
import com.ptun.app.controllers.dataoperations.DataUserOperations;
import com.ptun.app.statics.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.style.BorderBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import retrofit2.Call;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by Lenovo on 4/29/2017.
 */
public class ScanLogController implements Initializable {
    @FXML
    private DatePicker dpDari, dpSampai;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void showReport() throws DRException, IOException {
//        EasyLinkPoints easyLinkPoints = RetrofitBuilder.getInstance().create(EasyLinkPoints.class);
//        Call<AllScanLogs> allScanLogs = easyLinkPoints.allScanLogs(Constants.SN);
//        List<Scan> datas = allScanLogs.execute().body().getData();
//        Call
        AllScanLogs allScanLogs = GsonConverter.getIt().fromJson(new InputStreamReader(getClass().getResourceAsStream("/dataexample/scanlog.json")), AllScanLogs.class);
        AllUsers allUsers = GsonConverter.getIt().fromJson(new InputStreamReader(getClass().getResourceAsStream("/dataexample/users.json")), AllUsers.class);
        DataScanLogOperations dataScanLogOperations = new DataScanLogOperations(allScanLogs.getData());
        DataUserOperations dataUserOperations = new DataUserOperations(allUsers.getData());
//        dataScanLogOperations.groupByDateThenPIN().entrySet()
//                .stream().forEach(stringMapEntry -> stringMapEntry.getValue().entrySet()
//                .stream().forEach(stringListEntry -> System.out.println(dataUserOperations.findByPIN(stringListEntry.getKey()))));
        dataScanLogOperations.groupByDateThenPIN().entrySet().stream().forEach(stringMapEntry -> System.out.println(stringMapEntry.getKey()));
        List<String> keys = new ArrayList<>(dataScanLogOperations.groupByDateThenPIN().keySet());
        Collections.sort(keys);
        keys.stream().forEach(s -> System.out.println(s));
//        System.out.println(GsonConverter.getIt().toJson(dataScanLogOperations.groupByDateThenPIN()));
//        BorderBuilder borderBuilder = stl.border();
//        borderBuilder.setBottomPen(stl.penDouble())
//                .setLeftPen(stl.penThin())
//                .setTopPen(stl.penThin())
//                .setRightPen(stl.penThin());
//        StyleBuilder styleBuilder = stl.style().setBorder(borderBuilder);
//        report()
//                .columns(
//                        col.column("PIN", "PIN", type.stringType()),
//                        col.column("Nama", "name", type.stringType())
//                )
//                .setTemplateDesign(getClass().getClassLoader().getResource("absensikaryawan.jrxml"))
//                .pageFooter(cmp.pageXofY())
//                .setDataSource(datas)
//                .toPdf(new FileOutputStream("cobain.pdf"))
//                .show();
    }

    @FXML
    private void downloadReport() throws FileNotFoundException, DRException {


    }

}
