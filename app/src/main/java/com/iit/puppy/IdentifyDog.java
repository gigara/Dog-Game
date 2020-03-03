package com.iit.puppy;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.iit.puppy.Constants.IMAGES;

public class IdentifyDog extends AppCompatActivity {
    TextView answer;
    ImageView dogImage1;
    ImageView dogImage2;
    ImageView dogImage3;
    String expectedBreed;
    TextView countdown;
    //Declare timer
    CountDownTimer cTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_dog);

        dogImage1 = findViewById(R.id.identifyDogImage1);
        dogImage2 = findViewById(R.id.identifyDogImage2);
        dogImage3 = findViewById(R.id.identifyDogImage3);
        TextView dogName = findViewById(R.id.dogName);
        answer = findViewById(R.id.answer);
        countdown = findViewById(R.id.countdown2);

        boolean isAdvanced = getIntent().getBooleanExtra("IS_ADVANCED", false);

        // load random breed
        Random generator = new Random();
        List<String> keys = new ArrayList<String>(IMAGES.keySet());
        String generatedBreed1 = keys.get(generator.nextInt(keys.size()));
        String generatedBreed2 = keys.get(generator.nextInt(keys.size()));
        String generatedBreed3 = keys.get(generator.nextInt(keys.size()));

        List<String> breedImages1 = IMAGES.get(generatedBreed1);
        List<String> breedImages2 = IMAGES.get(generatedBreed2);
        List<String> breedImages3 = IMAGES.get(generatedBreed3);

        // load random images from the selected breed
        String dog1 = breedImages1.get(generator.nextInt(breedImages1.size()));
        String dog2;
        do {
            dog2 = breedImages2.get(generator.nextInt(breedImages2.size()));
        } while (dog1.equals(dog2));

        String dog3;
        do {
            dog3 = breedImages3.get(generator.nextInt(breedImages3.size()));
        } while (dog1.equals(dog3) && dog2.equals(dog3));

        String img1 = "Images/" + generatedBreed1 + "/" + dog1;
        String img2 = "Images/" + generatedBreed2 + "/" + dog2;
        String img3 = "Images/" + generatedBreed3 + "/" + dog3;

        // load image
        try {
            InputStream ims1 = getAssets().open(img1);
            InputStream ims2 = getAssets().open(img2);
            InputStream ims3 = getAssets().open(img3);

            // load image as Drawable
            Drawable d1 = Drawable.createFromStream(ims1, null);
            Drawable d2 = Drawable.createFromStream(ims2, null);
            Drawable d3 = Drawable.createFromStream(ims3, null);
            // set image to ImageView
            dogImage1.setImageDrawable(d1);
            dogImage1.setTag(generatedBreed1.split("-", 2)[1]);

            dogImage2.setImageDrawable(d2);
            dogImage2.setTag(generatedBreed2.split("-", 2)[1]);

            dogImage3.setImageDrawable(d3);
            dogImage3.setTag(generatedBreed3.split("-", 2)[1]);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // set name
        List<String> dogs = Arrays.asList(generatedBreed1, generatedBreed2, generatedBreed3);
        Collections.shuffle(dogs);
        expectedBreed = dogs.get(0).split("-", 2)[1];
        dogName.setText(expectedBreed);

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
                imageClick(null);
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

    public void imageClick(View view) {
        cancelTimer();
        String imageName = view != null ? (String) view.getTag() : "";
        if (imageName.equals(expectedBreed)) {
            answer.setText(R.string.correct);
            answer.setTextColor(Color.GREEN);
        } else {
            answer.setText(R.string.wrong);
            answer.setTextColor(Color.RED);
        }
        dogImage1.setClickable(false);
        dogImage2.setClickable(false);
        dogImage3.setClickable(false);
    }

    public void next(View view) {
        finish();
        startActivity(getIntent());
    }
}
