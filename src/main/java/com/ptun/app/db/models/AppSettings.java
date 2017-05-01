package com.ptun.app.db.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ptun.app.db.DB;
import com.ptun.app.statics.Constants;
import lombok.Data;

import java.sql.SQLException;

/**
 * Created by Lenovo on 4/29/2017.
 */
@Data
@DatabaseTable(tableName = "settings")
public class AppSettings {
    @DatabaseField(width = 1, id = true)
    private int id;
    @DatabaseField
    private String username;
    @DatabaseField
    private String password;
    @DatabaseField
    private String ipServer;
    @DatabaseField
    private String portServer;

    public String getURL() {
        if (this.ipServer == null || portServer == null)
            return Constants.BASE_URL;
        return String.format("http://%s:%s", ipServer, portServer);
    }

    public static Dao<AppSettings, Integer> getDao() {
        try {
            return DaoManager.createDao(DB.getDB(), AppSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
