package com.example.citysimulator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Iterator;
import java.util.LinkedList;

public class ScreenTitle extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_title);

        Button settings = (Button) findViewById(R.id.settingsButton);
        Button mapStart = (Button) findViewById(R.id.mapButton);

        //Singleton GameData, Set default values.
        GameData game = GameData.getInstance();
        game.setSettings(new Settings(50,10,1000,4,6,
                        10,0.3,2,100,500,20));
        game.loadDatabase(getApplicationContext());

        settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Launches Settings screen.
                Intent intent = new Intent(ScreenTitle.this, ScreenSettings.class);
                startActivity(intent);
            }
        });

        mapStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GameData game = GameData.getInstance();

                /* If game.getMap() is null, it means the user hasn't entered any values for
                    width/height in the settings screen. */
                if(game.getMap() == null)
                {
                    //Check if there's width/height values in database
                    DBModel database = game.getDatabase();
                    DBContainer1 values = database.getData1();
                    LinkedList<DBContainer2> savedStructures = database.getData2();

                    if(values != null)
                    {
                        //If there is values in database, use those
                        GameData.getInstance().getSettings().setMapWidth(values.getWidth());
                        GameData.getInstance().getSettings().setMapHeight(values.getHeight());
                        GameData.getInstance().getSettings().setInitialMoney(values.getMoney());
                        GameData.getInstance().setMoney(values.getMoney());
                        GameData.getInstance().setMap(MapData.getInstance(values.getWidth(), values.getHeight()).getGrid());

                        //If there are saved structures in the database, add those as well.
                        if(savedStructures != null)
                        {
                            Iterator<DBContainer2> iterator = savedStructures.iterator();
                            while (iterator.hasNext())
                            {
                                DBContainer2 structure = iterator.next();
                                if(structure.getType() == 1)
                                {
                                    game.getMap()[structure.getX()][structure.getY()].
                                            setStructure(new Residential(structure.getStructure()));
                                    game.addStructure(new Residential(structure.getStructure()));
                                }
                                else if(structure.getType() == 2)
                                {
                                    game.getMap()[structure.getX()][structure.getY()].
                                            setStructure(new Commercial(structure.getStructure()));
                                    game.addStructure(new Commercial(structure.getStructure()));
                                }
                                else if(structure.getType() == 3)
                                {
                                    game.getMap()[structure.getX()][structure.getY()].
                                            setStructure(new Road(structure.getStructure()));
                                    game.addStructure(new Road(structure.getStructure()));
                                }
                            }
                        }

                        Intent intent = new Intent(ScreenTitle.this,ScreenMap.class);
                        startActivity(intent);
                    }
                    else
                    {
                        //If no values in database, tell user to enter values in Settings screen
                        AlertDialog alertDialog = new AlertDialog.Builder(ScreenTitle.this)
                                                                                    .create();
                        alertDialog.setTitle("No Previous Game Found");
                        alertDialog.setMessage("Please set values.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                else
                {
                    //If the user has entered values in the Settings screen
                    Intent intent = new Intent(ScreenTitle.this,ScreenMap.class);
                    startActivity(intent);
                }
            }
        });
    }
}