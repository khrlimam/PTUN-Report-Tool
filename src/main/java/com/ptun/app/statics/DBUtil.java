package com.ptun.app.statics;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ptun.app.apis.GsonConverter;
import com.ptun.app.apis.enpoints.models.Scan;
import com.ptun.app.db.DB;
import com.ptun.app.db.models.AppSettings;
import com.ptun.app.db.models.TimeManagement;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 4/29/2017.
 */
public class DBUtil {
    public static Class[] classes = {AppSettings.class, TimeManagement.class};

    public static void migrateUp() {
        ConnectionSource cs = DB.getDB();
        for (Class c : classes) {
            try {
                TableUtils.createTableIfNotExists(cs, c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void migrateDown() {
        ConnectionSource cs = DB.getDB();
        for (Class c : classes) {
            try {
                TableUtils.dropTable(cs, c, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initSettingData() {
        try {
            AppSettings appSettings = new AppSettings();
            appSettings.setId(1);
            appSettings.setUsername("admin");
            appSettings.setPassword("admin");
            appSettings.setIpServer("192.168.8.3");
            appSettings.setPortServer("7005");
            AppSettings.getDao().createIfNotExists(appSettings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateData() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/dataexample/scanlog.json");
            Map data = new HashMap<>();
            List<Scan> scan = new ArrayList<>();
            data.put("Result", true);
            DateTime dateTime = new DateTime();
            for (int i=1;i<=1000; i++) {
                Scan sPin111In = new Scan(), sPin111Out = new Scan(), sPin112In = new Scan(), sPin112Out = new Scan();
                sPin111In.setSN(Constants.SN);
                sPin111Out.setSN(Constants.SN);
                sPin112In.setSN(Constants.SN);
                sPin112Out.setSN(Constants.SN);

                sPin111In.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                sPin111Out.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                sPin112In.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                sPin112Out.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                dateTime = dateTime.plusDays(1);

                sPin111In.setPIN("111");
                sPin111Out.setPIN("111");
                sPin112In.setPIN("113");
                sPin112Out.setPIN("113");

                sPin111In.setWorkCode(1);
                sPin111Out.setWorkCode(1);
                sPin112In.setWorkCode(1);
                sPin112Out.setWorkCode(1);

                sPin111In.setVerifyMode(1);
                sPin111Out.setVerifyMode(1);
                sPin112In.setVerifyMode(1);
                sPin112Out.setVerifyMode(1);

                sPin111In.setIOMode(1);
                sPin111Out.setIOMode(2);
                sPin112In.setIOMode(1);
                sPin112Out.setIOMode(2);

                scan.add(sPin111In);
                scan.add(sPin111Out);
                scan.add(sPin112In);
                scan.add(sPin112Out);
            }
            data.put("Data", scan);
            String scanlog = GsonConverter.transform().toJson(data);
            System.out.println(scanlog);
            fileWriter.write(scanlog);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
