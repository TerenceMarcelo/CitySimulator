package com.example.citysimulator;

public class Residential extends Structure
{
    int imageID;

    public Residential(int inID)
    {
        imageID = inID;
    }
    public void setImageID(int inID)
    {
        imageID = inID;
    }
    public int getImageID()
    {
        return imageID;
    }
}
