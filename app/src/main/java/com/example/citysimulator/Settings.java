package com.example.citysimulator;

public class Settings
{
    private int mapWidth, mapHeight, initialMoney, familySize, shopSize, salary, serviceCost,
                        houseBuildingCost, commBuildingCost, roadBuildingCost;
    private double taxRate;


    public Settings(int inWidth, int inHeight, int inMoney, int inFamily, int inShop, int inSalary,
                    double inTax, int inService, int inHouse, int inComm, int inRoad)
    {
        mapWidth = inWidth;
        mapHeight = inHeight;
        initialMoney = inMoney;
        familySize = inFamily;
        shopSize = inShop;
        salary = inSalary;
        taxRate = inTax;
        serviceCost = inService;
        houseBuildingCost = inHouse;
        commBuildingCost = inComm;
        roadBuildingCost = inRoad;
    }

    public int getMapWidth()
    {
        return mapWidth;
    }

    public int getMapHeight()
    {
        return mapHeight;
    }

    public int getInitialMoney()
    {
        return initialMoney;
    }

    public int getFamilySize()
    {
        return familySize;
    }

    public int getShopSize()
    {
        return shopSize;
    }

    public int getSalary()
    {
        return salary;
    }

    public int getServiceCost()
    {
        return serviceCost;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public int getHouseBuildingCost()
    {
        return houseBuildingCost;
    }

    public int getCommBuildingCost()
    {
        return commBuildingCost;
    }

    public int getRoadBuildingCost()
    {
        return roadBuildingCost;
    }

    public void setMapWidth(int inWidth)
    {
        mapWidth = inWidth;
    }

    public void setMapHeight(int inHeight)
    {
        mapHeight = inHeight;
    }

    public void setInitialMoney(int inMoney)
    {
        initialMoney = inMoney;
    }

    public void setFamilySize(int inSize)
    {
        familySize = inSize;
    }

    public void setShopSize(int inSize)
    {
        shopSize = inSize;
    }

    public void setSalary(int inSalary)
    {
        salary = inSalary;
    }

    public void setServiceCost(int inCost)
    {
        serviceCost = inCost;
    }

    public void setHouseBuildingCost(int inCost)
    {
        houseBuildingCost = inCost;
    }

    public void setCommBuildingCost(int inCost)
    {
        commBuildingCost = inCost;
    }

    public void setRoadBuildingCost(int inCost)
    {
        roadBuildingCost = inCost;
    }
}
