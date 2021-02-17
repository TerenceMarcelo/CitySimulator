package com.example.citysimulator;

import java.util.Random;

/**
 * Represents the overall map, and contains a grid of MapElement objects (accessible using the
 * get(row, col) method). The two static constants WIDTH and HEIGHT indicate the size of the map.
 *
 * There is a static get() method to be used to obtain an instance (rather than calling the
 * constructor directly).
 *
 * There is also a regenerate() method. The map is randomly-generated, and this method will invoke
 * the algorithm again to replace all the map data with a new randomly-generated grid.
 */
public class MapData
{

    private static final int[] GRASS = {R.drawable.ic_grass1, R.drawable.ic_grass2,
            R.drawable.ic_grass3, R.drawable.ic_grass4};

    private static final Random rng = new Random();
    private MapElement[][] grid;
    private int width, height;

    private static MapData instance = null;

    public MapData(int inWidth, int inHeight)
    {
        width = inWidth;
        height = inHeight;
        this.grid = generateGrid(width, height);
    }

    public static MapData getInstance(int inWidth, int inHeight)
    {
        if(instance == null)
        {
            instance = new MapData(inWidth,inHeight);
        }
        return instance;
    }

    public static MapData getCleanMap(int inWidth, int inHeight)
    {
        instance = new MapData(inWidth,inHeight);
        return instance;
    }

    private static MapElement[][] generateGrid(int WIDTH, int HEIGHT)
    {
        final int HEIGHT_RANGE = 256;
        final int INLAND_BIAS = 24;
        final int AREA_SIZE = 1;
        final int SMOOTHING_ITERATIONS = 2;

        int id = 1;

        int[][] heightField = new int[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++)
        {
            for(int j = 0; j < WIDTH; j++)
            {
                heightField[i][j] =
                    rng.nextInt(HEIGHT_RANGE)
                    + INLAND_BIAS * (
                        Math.min(Math.min(i, j), Math.min(HEIGHT - i - 1, WIDTH - j - 1)) -
                        Math.min(HEIGHT, WIDTH) / 4);
            }
        }

        int[][] newHf = new int[HEIGHT][WIDTH];
        for(int s = 0; s < SMOOTHING_ITERATIONS; s++)
        {
            for(int i = 0; i < HEIGHT; i++)
            {
                for(int j = 0; j < WIDTH; j++)
                {
                    int areaSize = 0;
                    int heightSum = 0;

                    for(int areaI = Math.max(0, i - AREA_SIZE);
                            areaI < Math.min(HEIGHT, i + AREA_SIZE + 1);
                            areaI++)
                    {
                        for(int areaJ = Math.max(0, j - AREA_SIZE);
                                areaJ < Math.min(WIDTH, j + AREA_SIZE + 1);
                                areaJ++)
                        {
                            areaSize++;
                            heightSum += heightField[areaI][areaJ];
                        }
                    }

                    newHf[i][j] = heightSum / areaSize;
                }
            }

            int[][] tmpHf = heightField;
            heightField = newHf;
            newHf = tmpHf;
        }

        MapElement[][] grid = new MapElement[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++)
        {
            for(int j = 0; j < WIDTH; j++)
            {
                MapElement element;
                grid[i][j] = new MapElement(choose(), null, id);
                id++;
            }
        }
        return grid;
    }

    private static int choose()
    {
        return GRASS[rng.nextInt(GRASS.length)];
    }

    public void regenerate()
    {
        this.grid = generateGrid(width, height);
    }

    public MapElement get(int i, int j)
    {
        return grid[i][j];
    }

    public MapElement[][] getGrid()
    {
        return grid;
    }
}
