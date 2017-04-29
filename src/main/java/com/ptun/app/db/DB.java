package com.ptun.app.db;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.DerbyEmbeddedDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.ptun.app.statics.Constants;

import java.sql.SQLException;

/**
 * Created by Lenovo on 4/29/2017.
 */
public class DB {

    private static ConnectionSource connectionSource;

    public static ConnectionSource getDB() {
        if (connectionSource != null)
            return connectionSource;
        try {
            DatabaseType databaseType = new DerbyEmbeddedDatabaseType();
            String protocol = "jdbc:derby:";
            String dbUrl = String.format("%s%s;create=true", protocol, Constants.DB_NAME);
            connectionSource = new JdbcConnectionSource(dbUrl, databaseType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionSource;
    }

}
