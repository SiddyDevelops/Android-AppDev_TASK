package com.siddyDevelops.figmaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public void redirectStage1(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Stage1Activity.class);
        startActivity(intent);
    }

    public void redirectStage2(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Stage2Activity.class);
        startActivity(intent);
    }

    public void redirectStage3(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Stage3Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}