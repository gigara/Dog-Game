package com.iit.puppy;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.iit.puppy.Constants.IMAGES;

public class IdentifyBreed extends AppCompatActivity {
    Spinner breedSpinner;
    TextView result;
    TextView countdown;
    Button submit;
    String generatedBreed;
    //Declare timer
    CountDownTimer cTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isAdvanced = getIntent().getBooleanExtra("IS_ADVANCED", false);
        setContentView(R.layout.activity_identify_breed);

        // load spinner data
        breedSpinner = findViewById(R.id.breedsSpinner);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.getBreedNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(adapter);
//        breedSpinner.setOnItemSelectedListener(this);

        ImageView dogImage = findViewById(R.id.identifyBreedImage);
        result = findViewById(R.id.result);
        submit = findViewById(R.id.submitBreed);
        countdown = findViewById(R.id.countdown);

        // load random breed
        Random generator = new Random();
        List<String> keys = new ArrayList<String>(IMAGES.keySet());
        generatedBreed = keys.get(generator.nextInt(keys.size()));
        List<String> breedImages = IMAGES.get(generatedBreed);

        // load random image from the selected breed
        String img = "Images/" + generatedBreed + "/" + breedImages.get(generator.nextInt(breedImages.size()));

        // load image
        try (InputStream ims = getAssets().open(img)) {
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            dogImage.setImageDrawable(d);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // level 2
        if (isAdvanced) startTimer();
    }

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown.setText(String.valueOf(millisUntilFinished/1000));
            }
            public void onFinish() {
                submit(null);
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

    public void submit(View view) {
        cancelTimer();
        if (submit.getText().toString().equals(getResources().getString(R.string.submit))) {
            String userInput = Constants.getBreedNames().get(breedSpinner.getSelectedItemPosition());
            if (userInput.equals(generatedBreed.split("-",2)[1])) {
                result.setText(R.string.correct);
                result.setTextColor(Color.GREEN);
            } else {
                result.setText(R.string.wrong);
                result.setTextColor(Color.RED);
            }
            submit.setText(R.string.next);
        } else {
            finish();
            startActivity(getIntent());
        }
    }
}
