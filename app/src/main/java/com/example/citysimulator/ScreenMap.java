package com.example.citysimulator;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScreenMap extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_map);

        // Display message on how to play if it's a new game
        if(GameData.getInstance().getTime() == 0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(ScreenMap.this).create();
            alertDialog.setTitle("How To Play");
            alertDialog.setMessage(
                    "Select 'Run' to progress and update the game information displayed.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentSelector fragmentSelector = (FragmentSelector)
                                                            fm.findFragmentById(R.id.selectorFrame);
        if(fragmentSelector == null)
        {
            fragmentSelector = new FragmentSelector();
            fm.beginTransaction().add(R.id.selectorFrame, fragmentSelector).commit();
        }
        FragmentMap mapFragment = (FragmentMap)fm.findFragmentById(R.id.mapFrame);
        if(mapFragment == null)
        {
            mapFragment = new FragmentMap();
            fm.beginTransaction().add(R.id.mapFrame, mapFragment).commit();
        }

        handleWeather();

        //Handle all the button clicks below
        Button run = (Button) findViewById(R.id.runButton);
        Button details = (Button) findViewById(R.id.detailsButton);
        Button pause = (Button) findViewById(R.id.pauseButton);
        ImageButton demolish = (ImageButton) findViewById(R.id.demolishButton);

        run.setOnClickListener(new View.OnClickListener()
        {
            /* Whenever user clicks 'run' button, Update displayed values. */
            @Override
            public void onClick(View view)
            {
                //Whenever, the user selects 'Run', all the values displayed are updated.
                GameData game = GameData.getInstance();
                game.timeIncrease(); //Updates all the values in GameData object
                TextView time = (TextView)findViewById(R.id.currentTime);
                TextView population = (TextView)findViewById(R.id.currentPopulation);
                TextView employment = (TextView)findViewById(R.id.employmentRate);
                TextView recentIncome = (TextView)findViewById(R.id.recentIncome);
                TextView cash = (TextView)findViewById(R.id.currentCash);
                time.setText("Current Time:\n" + game.getTime());
                population.setText("Population:\n" + game.getPopulation() + "\n");
                DecimalFormat df = new DecimalFormat("0.00");
                employment.setText("Employment\nRate:\n" + df.format((Double)game.getEmployment()));
                recentIncome.setText("Recent\nIncome:\n$ " + game.getIncomeLoss());
                cash.setText("Available Cash:\n$" + GameData.getInstance().getMoney());


                //Display Game Over when cash falls below $0
                if(game.getMoney() < 0)
                {
                    TextView instructions = (TextView)findViewById(R.id.instructions);
                    instructions.setText("GAME OVER. You can continue playing tho!");
                    instructions.setTextColor(Color.RED);
                }
                //Download new weather info and display that as well
                handleWeather();
            }
        });
        run.performClick();

        /* When user clicks on demolish or details, set it's respective value to true
           Next time user clicks on the map, the fragment will know it supposed to demolish
           or show details screen. */
        details.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GameData.getInstance().setDetailsButton(true);
            }
        });
        demolish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GameData.getInstance().setDemolishButton(true);
            }
        });

        //Just returns to the main screen if user selects pause.
        pause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }


    private void handleWeather()
    {
        MyTask task = new MyTask();
        task.execute();
    }

    //For downloading weather info
    private class MyTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected String doInBackground(Void... params)
        {
            String temp = "Temperature:\nN/A";
            try
            {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?id=2063523&units=metric&appid=9ba0a8d7aac762dc98ee3748719b2b2e");
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();


                try
                {
                    if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                    {
                        throw new IOException();
                    }

                    InputStream is = conn.getInputStream();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead = is.read(buffer);
                    while(bytesRead > 0)
                    {
                        baos.write(buffer, 0, bytesRead);
                        bytesRead = is.read(buffer);
                    }
                    baos.close();
                    String result = new String(baos.toByteArray());

                    JSONObject jBase = new JSONObject(result);

                    JSONObject sysObj = jBase.getJSONObject("main");
                    temp = sysObj.getString("temp");
                }
                catch (JSONException e)
                {
                    temp = "N/A";
                    e.printStackTrace();
                }
                finally
                {
                    conn.disconnect();
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return temp;
        }

        @Override
        protected void onPostExecute(String result)
        {
            TextView temp = (TextView)findViewById(R.id.currentTemp);
            temp.setText("Temperature:\n" + result + "º");
        }
    }
}