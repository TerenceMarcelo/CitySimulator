package com.example.citysimulator;

import android.content.Context;
import android.widget.Button;

import java.util.*;
import java.lang.Math.*;

import static java.lang.Math.min;

public class GameData
{
    private static GameData instance = null;
    private static MapElement[][] map = null;
    private static Settings settings;
    private static int money, gameTime, population, incomeLoss;
    private static int residentialCount, commercialCount, roadsCount;
    private static double employment;
    private static boolean demolishButton;
    private static boolean detailsButton;
    private Structure currentSelection;
    private static DBModel database;

    GameData()
    {
        gameTime = 0;
        population = 0;
        incomeLoss = 0;
        residentialCount = 0;
        commercialCount = 0;
        roadsCount = 0;
        demolishButton = false;
        detailsButton = false;
        database = new DBModel();
    }

    public static GameData getInstance()
    {
        if(instance == null)
        {
            instance = new GameData();
        }

        return instance;
    }

    public void loadDatabase(Context inContext)
    {
        database.load(inContext);
    }

    public DBModel getDatabase()
    {
        return database;
    }

    public void setSettings(Settings inSettings)
    {
        settings = inSettings;

    }

    public Settings getSettings()
    {
        return settings;
    }

    public int getTime()
    {
        return gameTime;
    }

    public int getPopulation()
    {
        return population;
    }

    public double getEmployment()
    {
        return employment;
    }

    public void setDemolishButton(boolean inDemolish)
    {
        demolishButton = inDemolish;
    }

    public boolean demolishButton()
    {
        return demolishButton;
    }

    public void setDetailsButton(boolean inDetails)
    {
        detailsButton = inDetails;
    }

    public boolean detailsButton()
    {
        return detailsButton;
    }

    public void setMap(MapElement[][] inMap)
    {
        map = inMap;
    }

    public MapElement[][] getMap()
    {
        return map;
    }

    public void setMoney(int inMoney)
    {
        money = inMoney;
    }

    public int getMoney()
    {
        return  money;
    }

    public int getIncomeLoss()
    {
        return  incomeLoss;
    }

    public Structure getCurrentSelection()
    {
        return currentSelection;
    }

    public void setCurrentSelection(Structure inSelection)
    {
        currentSelection = inSelection;
    }

    public int getRoadsCount()
    {
        return roadsCount;
    }

    public void clearData()
    {
        gameTime = 0;
        population = 0;
        incomeLoss = 0;
        residentialCount = 0;
        commercialCount = 0;
        roadsCount = 0;
        demolishButton = false;
        detailsButton = false;
        map = null;
    }

    public void timeIncrease()
    {
        population = settings.getFamilySize() * residentialCount;
        if(population != 0)
        {
            employment = min(1, (double)(commercialCount * settings.getShopSize()) / (double)population);
        }
        else
        {
            employment = 0;
        }
        int prevMoney = money;
        money += population * (employment * settings.getSalary() * settings.getTaxRate() - settings.getServiceCost());
        incomeLoss = money - prevMoney;

        gameTime++;
    }

    /*Keeping track of how many structures for the formulas used to count population,
        employment rate, etc.
     */
    public void addStructure(Structure inStructure)
    {
        if (inStructure instanceof Residential)
        {
            setMoney(getMoney() - getSettings().getHouseBuildingCost());
            residentialCount++;
        }
        else if (inStructure instanceof Commercial)
        {
            setMoney(getMoney() - getSettings().getCommBuildingCost());
            commercialCount++;
        } else if (inStructure instanceof Road)
        {
            setMoney(getMoney() - getSettings().getRoadBuildingCost());
            roadsCount++;
        }
    }

    public void removeStructure(Structure inStructure)
    {
        if(inStructure instanceof Residential)
        {
            residentialCount--;
        }
        else if(inStructure instanceof Commercial)
        {
            commercialCount--;
        }
        else if(inStructure instanceof Road)
        {
            roadsCount--;
        }
    }

    public int getStructureCount()
    {
        return residentialCount + commercialCount + roadsCount;
    }
}
