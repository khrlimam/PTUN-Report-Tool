package com.ptun.app.statics;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ptun.app.apis.GsonConverter;
import com.ptun.app.apis.enpoints.models.Scan;
import com.ptun.app.db.DB;
import com.ptun.app.db.models.AppSettings;
import com.ptun.app.db.models.Time;
import com.ptun.app.db.models.TimeManagement;
import com.ptun.app.db.models.User;
import com.ptun.app.enums.PEGAWAI_CHOICES;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Lenovo on 4/29/2017.
 */
public class DBUtil {
    public static Class[] classes = {AppSettings.class, TimeManagement.class, User.class};

    public static void migrateUp() {
        ConnectionSource cs = DB.getDB();
        Arrays.stream(classes).forEach(aClass -> {
            try {
                TableUtils.createTableIfNotExists(cs, aClass );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void migrateDown() {
        ConnectionSource cs = DB.getDB();
        Arrays.stream(classes).forEach(aClass -> {
            try {
                TableUtils.dropTable(cs, aClass, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
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

    public static void initTimeManagementData() {
        TimeManagement timeManagement = new TimeManagement();
        timeManagement.setId(Constants.SETTING_ID);
        timeManagement.setEarlyTolerance(10);
        timeManagement.setLateTolerance(10);
        timeManagement.setOffDuty(new Time("16:30"));
        timeManagement.setOnDuty(new Time("08:00"));
        try {
            TimeManagement.getDao().createIfNotExists(timeManagement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetDB() {
        migrateDown();
        migrateUp();
        initData();
    }

    public static void bootUpDB() {
        migrateUp();
        initData();
    }

    public static void initData() {
        initSettingData();
        initTimeManagementData();
        generateDummyDBUserData();
    }

    public static void generateDummyData() {
        generateDummyDBUserData();
        generateScanLogJsonData();
    }

    public static void generateDummyDBUserData() {
        User userPin1 = new User();
        User userPin2 = new User();
        User userPin3 = new User();

        userPin1.setPIN(1);
        userPin1.setJabatan(PEGAWAI_CHOICES.HAKIM.name());
        userPin2.setPIN(2);
        userPin2.setJabatan(PEGAWAI_CHOICES.STAFF.name());
        userPin3.setPIN(3);
        userPin3.setJabatan(PEGAWAI_CHOICES.STAFF.name());
        try {
            User.getDao().createIfNotExists(userPin1);
            User.getDao().createIfNotExists(userPin2);
            User.getDao().createIfNotExists(userPin3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateScanLogJsonData() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/dataexample/scanlog.json");
            Map data = new HashMap<>();
            List<Scan> scan = new ArrayList<>();
            data.put("Result", true);
            DateTime dateTime = new DateTime();
            for (int i=1;i<=1000; i++) {
                Scan sPin111In = new Scan(), sPin111Out = new Scan(), sPin112In = new Scan(), sPin112Out = new Scan(), sPin113In = new Scan(), sPin113Out = new Scan();
                sPin111In.setSN(Constants.SN);
                sPin111Out.setSN(Constants.SN);
                sPin112In.setSN(Constants.SN);
                sPin112Out.setSN(Constants.SN);
                sPin113In.setSN(Constants.SN);
                sPin113Out.setSN(Constants.SN);

                sPin111In.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                sPin111Out.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                sPin112In.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                sPin112Out.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                sPin113In.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));
                sPin113Out.setScanDate(dateTime.toString(DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN)));

                dateTime = dateTime.plusDays(1);

                sPin111In.setPIN("1");
                sPin111Out.setPIN("1");
                sPin112In.setPIN("2");
                sPin112Out.setPIN("2");
                sPin113In.setPIN("3");
                sPin113Out.setPIN("3");

                sPin111In.setWorkCode(1);
                sPin111Out.setWorkCode(1);
                sPin112In.setWorkCode(1);
                sPin112Out.setWorkCode(1);
                sPin113In.setWorkCode(1);
                sPin113Out.setWorkCode(1);

                sPin111In.setVerifyMode(1);
                sPin111Out.setVerifyMode(1);
                sPin112In.setVerifyMode(1);
                sPin112Out.setVerifyMode(1);
                sPin113In.setVerifyMode(1);
                sPin113Out.setVerifyMode(1);

                sPin111In.setIOMode(Constants.SCAN_IN);
                sPin111Out.setIOMode(Constants.SCAN_OUT);
                sPin112In.setIOMode(Constants.SCAN_IN);
                sPin112Out.setIOMode(Constants.SCAN_OUT);
                sPin113In.setIOMode(Constants.SCAN_IN);
                sPin113Out.setIOMode(Constants.SCAN_OUT);

                scan.add(sPin111In);
                scan.add(sPin111Out);
                scan.add(sPin112In);
                scan.add(sPin112Out);
                scan.add(sPin113In);
                scan.add(sPin113Out);
            }
            data.put("Data", scan);
            String scanlog = GsonConverter.transform().toJson(data);
            fileWriter.write(scanlog);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
