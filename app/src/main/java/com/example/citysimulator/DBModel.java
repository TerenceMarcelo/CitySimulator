package com.example.citysimulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public class DBModel
{
    private DBContainer1 data1;
    private LinkedList<DBContainer2> data2 = new LinkedList<DBContainer2>();
    private SQLiteDatabase db;

    public void load(Context context)
    {
        this.db = new DBHelper(context.getApplicationContext()).getWritableDatabase();
        query1();
        query2();
        //Load onto gameData
    }

    public void addData1(DBContainer1 inData)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBSchema.GameTable.Cols1.WIDTH, inData.getWidth());
        cv.put(DBSchema.GameTable.Cols1.HEIGHT, inData.getHeight());
        cv.put(DBSchema.GameTable.Cols1.MONEY, inData.getMoney());
        db.insert(DBSchema.GameTable.NAME1, null, cv);
        data1 = inData;
    }

    public void addData2(DBContainer2 inData)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBSchema.GameTable.Cols2.X, inData.getX());
        cv.put(DBSchema.GameTable.Cols2.Y, inData.getY());
        cv.put(DBSchema.GameTable.Cols2.STRUCTURE, inData.getStructure());
        cv.put(DBSchema.GameTable.Cols2.ID, inData.getId());
        cv.put(DBSchema.GameTable.Cols2.TYPE, inData.getType());
        db.insert(DBSchema.GameTable.NAME2, null, cv);
        data2.add(inData);
    }

    public void removeData1(DBContainer1 inData)
    {
        String[] whereValue = { String.valueOf(inData.getWidth())};
        db.delete(DBSchema.GameTable.NAME1,DBSchema.GameTable.Cols1.WIDTH + " = ?", whereValue);
        data1 = null;
    }

    public void removeData2(int inID)
    {
        String[] whereValue = { String.valueOf(inID)};
        db.delete(DBSchema.GameTable.NAME2,DBSchema.GameTable.Cols2.ID + " = ?", whereValue);
        db.execSQL("delete from "+ DBSchema.GameTable.NAME2);
        query2();
    }

    public void clearData2()
    {
        data2 = null;
        data2 = new LinkedList<DBContainer2>();
        db.delete(DBSchema.GameTable.NAME2, null, null);
    }

    public void query1()
    {
        DBCursor cursor = new DBCursor(db.query(DBSchema.GameTable.NAME1,
        null,null,null,null,null,null,null));
        try
        {
            if(cursor.moveToFirst() && cursor.getCount() != 0)
            {
                data1 = cursor.getData1();
            }
            else
            {
                data1 = null;
            }
        }
        finally
        {
            cursor.close();
        }
    }

    public void query2()
    {
        data2 = null;
        data2 = new LinkedList<DBContainer2>();

        DBCursor cursor = new DBCursor(db.query(DBSchema.GameTable.NAME2,
                null,null,null,null,null,null,null));
        try
        {
            if(cursor.moveToFirst() && cursor.getCount() != 0)
            {
                while(!cursor.isAfterLast())
                {
                    data2.add(cursor.getData2());
                    cursor.moveToNext();
                }
            }
        }
        finally
        {
            cursor.close();
        }
    }

    public DBContainer1 getData1()
    {
        return data1;
    }

    public LinkedList<DBContainer2> getData2()
    {
        return data2;
    }
}
