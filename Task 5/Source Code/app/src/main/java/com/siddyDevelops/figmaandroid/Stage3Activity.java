package com.siddyDevelops.figmaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class Stage3Activity extends AppCompatActivity {

    ImageView person1ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage3);

        person1ImageView = findViewById(R.id.person1ImageView);
        person1ImageView.setClipToOutline(true);
    }
}