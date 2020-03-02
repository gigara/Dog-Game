package com.iit.puppy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Arrays;

import static com.iit.puppy.Constants.BREEDS;
import static com.iit.puppy.Constants.IMAGES;

public class MainActivity extends AppCompatActivity {
    boolean isAdvanced = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Switch advancedLevel = findViewById(R.id.advancedSwitch);
        super.onCreate(savedInstanceState);
        advancedLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAdvanced = isChecked;
            }
        });

        // load all images into array
        try {
            BREEDS.addAll(Arrays.asList(getAssets().list("Images")));
            for (String breed : BREEDS) {
                IMAGES.put(breed, Arrays.asList(getAssets().list("Images/" + breed)));
            }
            int i = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void identifyBreed(View view) {
        Intent intent = new Intent(this, IdentifyBreed.class);
        intent.putExtra("IS_ADVANCED", isAdvanced);
        startActivity(intent);
    }

    public void identifyDog(View view) {
        Intent intent = new Intent(this, IdentifyDog.class);
        intent.putExtra("IS_ADVANCED", isAdvanced);
        startActivity(intent);
    }

    public void searchBreed(View view) {
        Intent intent = new Intent(this, SearchBreed.class);
        intent.putExtra("IS_ADVANCED", isAdvanced);
        startActivity(intent);
    }
}
