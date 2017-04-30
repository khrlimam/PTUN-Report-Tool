package com.ptun.app.statics;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ptun.app.apis.GsonConverter;
import com.ptun.app.apis.enpoints.models.Scan;
import com.ptun.app.db.DB;
import com.ptun.app.db.models.AppSettings;
import com.ptun.app.db.models.TimeManagement;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
            FileWriter fileWriter = new FileWriter("scanlog.json");
            Map data = new HashMap<>();
            List<Scan> scan = new ArrayList<>();
            data.put("Result", true);
            DateTime dateTime = new DateTime();
            for (int i=1;i<=500; i++) {
                Scan s = new Scan();
                s.setSN(Constants.SN);
                s.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                dateTime = dateTime.plusDays(1);
                s.setPIN("111");
                s.setWorkCode(1);
                s.setVerifyMode(1);
                s.setWorkCode(0);
                scan.add(s);
                s.setIOMode(2);
                scan.add(s);
                s.setPIN("112");
                s.setIOMode(1);
                scan.add(s);
                s.setIOMode(2);
                scan.add(s);
            }
            data.put("Data", scan);
            String scanlog = GsonConverter.getIt().toJson(data);
            System.out.println(scanlog);
            fileWriter.write(scanlog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
