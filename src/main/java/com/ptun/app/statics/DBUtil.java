package com.ptun.app.statics;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ptun.app.db.DB;
import com.ptun.app.db.models.AppSettings;
import com.ptun.app.db.models.TimeManagement;

import java.sql.SQLException;

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

}
