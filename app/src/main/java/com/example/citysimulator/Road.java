package com.example.citysimulator;

public class Road extends Structure
{
    int imageID;

    public Road(int inID)
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
