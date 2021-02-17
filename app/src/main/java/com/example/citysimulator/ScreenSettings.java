package com.example.citysimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScreenSettings extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_settings);

        Button save = (Button) findViewById(R.id.saveButton);
        Button cancel = (Button) findViewById(R.id.cancelButton);

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                int width, height, money;
                GameData game = GameData.getInstance();

                //The text boxes for user input
                EditText widthEntry = (EditText)findViewById(R.id.widthInput);
                EditText heightEntry = (EditText)findViewById(R.id.heightInput);
                EditText moneyEntry = (EditText)findViewById(R.id.moneyInput);

                //Checks that the user hasn't left any of the fields empty.
                if(TextUtils.isEmpty(moneyEntry.getText().toString()) ||
                   TextUtils.isEmpty(heightEntry.getText().toString()) ||
                   TextUtils.isEmpty(moneyEntry.getText().toString()))
                {
                    //Alert inside a text box if it's empty when the user selects 'save'
                    if(TextUtils.isEmpty(widthEntry.getText().toString()))
                    {
                        widthEntry.setError("Width must have a value.");
                    }
                    if(TextUtils.isEmpty(heightEntry.getText().toString()))
                    {
                        heightEntry.setError("Height must have a value.");
                    }
                    if(TextUtils.isEmpty(moneyEntry.getText().toString()))
                    {
                        moneyEntry.setError("Money must have a value.");
                    }
                }
                else if(Integer.parseInt(moneyEntry.getText().toString()) < 0)
                {
                    moneyEntry.setError("Money can't be negative.");
                }
                else if(Integer.parseInt(widthEntry.getText().toString()) > 200 ||
                        Integer.parseInt(widthEntry.getText().toString()) < 1)
                {
                    widthEntry.setError("Width must be between 1 and 200.");
                }
                else if(Integer.parseInt(heightEntry.getText().toString()) > 50 ||
                        Integer.parseInt(heightEntry.getText().toString()) < 1)
                {
                    heightEntry.setError("Height must be between 1 and 50.");
                }
                else
                {
                    //If all the values are valid:
                    //Remove old database entries
                    DBModel database = game.getDatabase();
                    DBContainer1 values = database.getData1();
                    if(values != null)
                    {
                        database.removeData1(values);
                    }
                    database.clearData2();
                    game.clearData();

                    width = Integer.parseInt(widthEntry.getText().toString());
                    height = Integer.parseInt(heightEntry.getText().toString());
                    money = Integer.parseInt(moneyEntry.getText().toString());

                    //Adds the values the user entered into GameData and also the Settings object.
                    game.getSettings().setMapWidth(width);
                    game.getSettings().setMapHeight(height);
                    game.getSettings().setInitialMoney(money);
                    game.setMoney(money);
                    game.setMap(MapData.getCleanMap(width, height).getGrid());

                    //Add new values to database
                    database.addData1(new DBContainer1(game.getSettings().getMapWidth(),
                                            game.getSettings().getMapHeight(), game.getMoney()));

                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Return to the title screen if user selects 'cancel'
                finish();
            }
        });
    }
}