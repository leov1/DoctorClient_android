package com.hxqydyl.app.ys.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by wx on 2015/12/3.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSON = 1;

    public DataBaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    private static DataBaseHelper dataBaseHelper;

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSON);
        }
        return dataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
//        try {
//            Log.e("wangxu", "onCreate");
////            TableUtils.createTable(connectionSource, DataBaseUser.class);
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }


//    public Dao<DataBaseUser, Integer> getDataBaseUser() throws SQLException {
//        return getDao(DataBaseUser.class);
//    }

}
