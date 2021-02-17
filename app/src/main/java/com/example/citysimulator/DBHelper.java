package com.example.citysimulator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.citysimulator.DBSchema;

public class DBHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "game.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + DBSchema.GameTable.NAME1 + "(" + DBSchema.GameTable.Cols1.WIDTH
                                + " INTEGER, " + DBSchema.GameTable.Cols1.HEIGHT + " INTEGER, " +
                                                     DBSchema.GameTable.Cols1.MONEY + " INTEGER)");

        db.execSQL("CREATE TABLE " + DBSchema.GameTable.NAME2 + "(" + DBSchema.GameTable.Cols2.X
                + " INTEGER, " + DBSchema.GameTable.Cols2.Y + " INTEGER, " +
                DBSchema.GameTable.Cols2.ID + " INTEGER, " +
                DBSchema.GameTable.Cols2.TYPE + " INTEGER, " +
                DBSchema.GameTable.Cols2.STRUCTURE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
