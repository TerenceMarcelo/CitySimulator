package com.example.citysimulator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScreenDetails extends AppCompatActivity
{
    int x, y;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_details);

        Button save = (Button) findViewById(R.id.saveButton);
        Button back = (Button) findViewById(R.id.backButton);
        ImageButton camera = (ImageButton) findViewById(R.id.imageButton);

        final Intent intent = getIntent();
        TextView xy = (TextView)findViewById(R.id.xyText);
        TextView structureType = (TextView)findViewById(R.id.structureText);
        EditText name = (EditText)findViewById(R.id.nameText);

        x = intent.getIntExtra("x", 0);
        y = intent.getIntExtra("y", 0);

        xy.setText("[ " + x + ", " + y + " ]");

        if(GameData.getInstance().getMap()[x][y].getName() != null)
        {
            name.setText(GameData.getInstance().getMap()[x][y].getName());
        }
        else
        {
            int structure = intent.getIntExtra("structureType", 1);
            if (structure == 1) {
                structureType.setText("Residential");
                name.setText("Residential");
            } else if (structure == 2) {
                structureType.setText("Commercial");
                name.setText("Commercial");
            } else {
                structureType.setText("Road");
                name.setText("Road");
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText name = (EditText)findViewById(R.id.nameText);
                String nameEntered = name.getText().toString();
                name.setText(nameEntered);
                GameData.getInstance().getMap()[x][y].setName(nameEntered);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        camera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final int REQUEST_THUMBNAIL = 1;

                try
                {
                    startActivityForResult(cameraIntent, REQUEST_THUMBNAIL);
                }
                catch(ActivityNotFoundException e)
                {

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            GameData game = GameData.getInstance();
            game.getMap()[x][y].setImage(imageBitmap);

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .create();
            alertDialog.setTitle("Photo Saved!");
            alertDialog.setMessage("Tap anywhere on the map to update to new image.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

}