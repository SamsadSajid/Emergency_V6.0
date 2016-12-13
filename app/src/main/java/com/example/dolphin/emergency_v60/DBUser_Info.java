package com.example.dolphin.emergency_v60;

/**
 * Created by DOLPHIN on 12/12/2016.
 */
import com.example.dolphin.emergency_v60.User_Info;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.Date;

public class DBUser_Info {
    private DBUser_Info.UserInfoHelper mHelper;
    private SQLiteDatabase mDatabase;

    public DBUser_Info(Context context) {
        System.out.println("Creating DBUser_Info class");
        mHelper = new DBUser_Info.UserInfoHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public boolean insertUserInfo(User_Info userInfo, boolean clearPrevious) {
        if (clearPrevious) {
            deleteAppearanceInfo();
        }

        String sql = "INSERT INTO " + DBUser_Info.UserInfoHelper.TABLE_APPEARANCE_INFO + " VALUES (?,?,?,?,?,?,?,?);";
        //compile the statement and start a transaction
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();

        statement.clearBindings();
        //for a given column index, simply bind the data to be put inside that index
        statement.bindString(2, userInfo.getName());
        statement.bindString(3, userInfo.getPhone());
        statement.bindString(4, userInfo.getAddress());
        statement.bindString(5, userInfo.getInstitution());
        statement.bindString(6, userInfo.getEmergency1());
        statement.bindString(7, userInfo.getEmergency2());
        statement.bindString(8, userInfo.getBloodgroup());
        statement.execute();
        System.out.println("Inserted appearance info successfully");
        //set the transaction as successful and end the transaction
        Msg.mymsg("inserting entries " + new Date(System.currentTimeMillis()));
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();

        return true;
    }

    public User_Info readAppearanceInfo() {
        User_Info userinfo = new User_Info();
        //userinfo.setId(-1);
        //get a list of columns to be retrieved, we need all of them
        String[] columns = {DBUser_Info.UserInfoHelper.COLUMN_ID,
                DBUser_Info.UserInfoHelper.COLUMN_Name,
                UserInfoHelper.COLUMN_Phone,
                DBUser_Info.UserInfoHelper.COLUMN_Address,
                DBUser_Info.UserInfoHelper.COLUMN_Institution,
                DBUser_Info.UserInfoHelper.COLUMN_Emergency1,
                DBUser_Info.UserInfoHelper.COLUMN_Emergency2,
                DBUser_Info.UserInfoHelper.COLUMN_Bloodgroup
        };
        Cursor cursor = mDatabase.query(DBUser_Info.UserInfoHelper.TABLE_APPEARANCE_INFO, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Msg.mymsg("loading entries " + cursor.getCount() + new Date(System.currentTimeMillis()));
            System.out.println("Getting elements......");
            //userinfo.setId(cursor.getInt(cursor.getColumnIndex(UserInfoHelper.COLUMN_ID)));
            userinfo.setName(cursor.getString(cursor.getColumnIndex(DBUser_Info.UserInfoHelper.COLUMN_Name)));
            userinfo.setPhone(cursor.getString(cursor.getColumnIndex(UserInfoHelper.COLUMN_Phone)));
            userinfo.setAddress(cursor.getString(cursor.getColumnIndex(DBUser_Info.UserInfoHelper.COLUMN_Address)));
            userinfo.setInstitution(cursor.getString(cursor.getColumnIndex(DBUser_Info.UserInfoHelper.COLUMN_Institution)));
            userinfo.setEmergency1(cursor.getString(cursor.getColumnIndex(DBUser_Info.UserInfoHelper.COLUMN_Emergency1)));
            userinfo.setEmergency2(cursor.getString(cursor.getColumnIndex(DBUser_Info.UserInfoHelper.COLUMN_Emergency2)));
            userinfo.setBloodgroup(cursor.getString(cursor.getColumnIndex(DBUser_Info.UserInfoHelper.COLUMN_Bloodgroup)));
        }
        cursor.close();
        return userinfo;
    }

    public void deleteAppearanceInfo() {
        mDatabase.delete(DBUser_Info.UserInfoHelper.TABLE_APPEARANCE_INFO, null, null);
    }

    private static class UserInfoHelper extends SQLiteOpenHelper {
        public static final String TABLE_APPEARANCE_INFO = "appearance_info";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_Name = "Name";
        public static final String COLUMN_Phone = "Phone";
        public static final String COLUMN_Address = "Address";
        public static final String COLUMN_Institution = "Institution";
        public static final String COLUMN_Emergency1 = "Emergency1";
        public static final String COLUMN_Emergency2 = "Emergency2";
        public static final String COLUMN_Bloodgroup = "Bloodgroup";

        private static final String CREATE_TABLE_APPEARANCE_INFO = "CREATE TABLE " + TABLE_APPEARANCE_INFO + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_Name + " TEXT," +
                COLUMN_Phone + " TEXT," +
                COLUMN_Address + " TEXT," +
                COLUMN_Institution + " TEXT," +
                COLUMN_Emergency1 + " TEXT," +
                COLUMN_Emergency2 + " TEXT," +
                COLUMN_Bloodgroup + " TEXT" +
                ");";

        private static final String DB_NAME = "appearanceInfo_db";
        private static final int DB_VERSION = 1;
        private Context mContext;

        public UserInfoHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_APPEARANCE_INFO);
                Msg.mymsg("Create table appearance info executed");
            } catch (SQLiteException exception) {
                Msg.cout(mContext, exception + "");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Msg.mymsg("upgrade table appearance info executed");
                db.execSQL(" DROP TABLE " + TABLE_APPEARANCE_INFO + " IF EXISTS;");
                onCreate(db);
            } catch (SQLiteException exception) {
                Msg.cout(mContext, exception + "");
            }
        }
    }
}