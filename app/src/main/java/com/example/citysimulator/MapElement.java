package com.example.citysimulator;

import android.graphics.Bitmap;

public class MapElement
{
    private boolean buildingBuildable;
    private int imageID;
    private String name;
    private Structure structure;
    private int id;
    private Bitmap image;

    public MapElement(int inImage, Structure structure, int inID)
    {
        this.buildingBuildable = false;
        this.structure = null;
        this.image = null;
        this.imageID = inImage;
        this.id = inID;
    }


    public boolean isBuildingBuildable()
    {
        return buildingBuildable;
    }

    public void setBuildingBuildable(boolean inBuild)
    {
        buildingBuildable = inBuild;
    }

    public int getImageID()
    {
        return imageID;
    }

    public int getID()
    {
        return id;
    }

    /**
     * Retrieves the structure built on this map element.
     * @return The structure, or null if one is not present.
     */
    public Structure getStructure()
    {
        return structure;
    }

    public void setStructure(Structure structure)
    {
        this.structure = structure;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap inImage)
    {
        image = inImage;
    }

    public void setName(String inName)
    {
        name = inName;
    }

    public String getName()
    {
        return name;
    }
}