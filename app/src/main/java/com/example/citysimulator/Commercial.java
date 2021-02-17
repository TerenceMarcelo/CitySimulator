package com.example.citysimulator;

public class Commercial extends Structure
{
    int imageID;

    public Commercial(int inID)
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
