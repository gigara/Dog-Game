package com.iit.puppy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void identifyBreed(View view) {
        Intent intent = new Intent(this, IdentifyBreed.class);
        startActivity(intent);
    }

    public void identifyDog(View view) {
        Intent intent = new Intent(this, IdentifyDog.class);
        startActivity(intent);
    }

    public void searchBreed(View view) {
        Intent intent = new Intent(this, SearchBreed.class);
        startActivity(intent);
    }
}
