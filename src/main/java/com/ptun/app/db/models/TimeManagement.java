package com.ptun.app.db.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ptun.app.db.DB;
import lombok.Data;

import java.sql.SQLException;

/**
 * Created by Lenovo on 4/29/2017.
 */
@Data
@DatabaseTable(tableName = "time_management")
public class TimeManagement {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Time onDuty;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Time offDuty;
    @DatabaseField
    private int lateTolerance;
    @DatabaseField
    private int earlyTolerance;

    public static Dao<TimeManagement, Integer> getDao() {
        try {
            return DaoManager.createDao(DB.getDB(), TimeManagement.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
