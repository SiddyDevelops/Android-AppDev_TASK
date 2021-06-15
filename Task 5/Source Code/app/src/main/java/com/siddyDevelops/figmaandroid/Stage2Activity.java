package com.siddyDevelops.figmaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class Stage2Activity extends AppCompatActivity {

    ImageView catImage;
    ImageView dogImage;
    ImageView hamsterImage;
    ImageView parrotImage;
    ImageView goldfishImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage2);

        catImage = findViewById(R.id.imageCat);
        dogImage = findViewById(R.id.imageDog);
        hamsterImage = findViewById(R.id.imageHamster);
        parrotImage = findViewById(R.id.imageParrot);
        goldfishImage = findViewById(R.id.imageGoldfish);

        catImage.setClipToOutline(true);
        dogImage.setClipToOutline(true);
        hamsterImage.setClipToOutline(true);
        parrotImage.setClipToOutline(true);
        goldfishImage.setClipToOutline(true);
    }
}