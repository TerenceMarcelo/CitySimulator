package com.example.citysimulator;

public class DBContainer1
{
    private int width;
    private int height;
    private int money;

    public DBContainer1(int inWidth, int inHeight, int inMoney)
    {
        width = inWidth;
        height = inHeight;
        money = inMoney;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getMoney()
    {
        return money;
    }
}
