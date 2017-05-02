package com.ptun.app.db.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ptun.app.db.DB;
import com.ptun.app.enums.PEGAWAI_CHOICES;
import lombok.Data;

import java.sql.SQLException;

/**
 * Created by khairulimam on 01/05/17.
 */
@Data
@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(id = true, width = 9)
    private int PIN;
    @DatabaseField
    private String jabatan;
    @DatabaseField
    private String nama;

    public static Dao<User, Integer> getDao() {
        try {
            return DaoManager.createDao(DB.getDB(), User.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
