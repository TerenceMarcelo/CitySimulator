package com.example.citysimulator;

import android.database.Cursor;
import android.database.CursorWrapper;

public class DBCursor extends CursorWrapper
{
    public DBCursor(Cursor cursor)
    {
        super(cursor);
    }

    public DBContainer1 getData1()
    {
        int width = getInt(getColumnIndex(DBSchema.GameTable.Cols1.WIDTH));
        int height = getInt(getColumnIndex(DBSchema.GameTable.Cols1.HEIGHT));
        int money = getInt(getColumnIndex(DBSchema.GameTable.Cols1.MONEY));

        return new DBContainer1(width,height,money);
    }

    public DBContainer2 getData2()
    {
        int x = getInt(getColumnIndex(DBSchema.GameTable.Cols2.X));
        int y = getInt(getColumnIndex(DBSchema.GameTable.Cols2.Y));
        int container = getInt(getColumnIndex(DBSchema.GameTable.Cols2.STRUCTURE));
        int id = getInt(getColumnIndex(DBSchema.GameTable.Cols2.ID));
        int type = getInt(getColumnIndex(DBSchema.GameTable.Cols2.TYPE));

        return new DBContainer2(x,y,container,id, type);
    }
}
