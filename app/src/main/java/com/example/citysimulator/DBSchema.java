package com.example.citysimulator;

public class DBSchema
{
    public static class GameTable
    {
        public static final String NAME1 = "GameValues";
        public static class Cols1
        {
            public static final String WIDTH = "map_width";
            public static final String HEIGHT = "map_height";
            public static final String MONEY = "current_money";
        }

        public static final String NAME2= "Structures";
        public static class Cols2
        {
            public static final String X = "x_pos";
            public static final String Y = "y_pos";
            public static final String STRUCTURE = "structure_image";
            public static final String ID = "entry_id";
            public static final String TYPE = "structure_type";
        }
    }
}
