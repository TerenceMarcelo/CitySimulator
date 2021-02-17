package com.example.citysimulator;

public class DBContainer2
{
    private int x;
    private int y;
    private int structure;
    private int id;
    private int type;

    public DBContainer2(int inX, int inY, int inStructure, int inID, int inType)
    {
        x = inX;
        y = inY;
        structure = inStructure;
        id = inID;
        type = inType;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getType()
    {
        return  type;
    }

    public int getStructure()
    {
        return structure;
    }

    public int getId() { return id; }
}
