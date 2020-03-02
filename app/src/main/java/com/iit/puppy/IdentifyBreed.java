package com.iit.puppy;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
    Button submit;
    String generatedBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isAdvanced = Boolean.getBoolean(getIntent().getStringExtra("IS_ADVANCED"));
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
    }

    public void submit(View view) {
        if (submit.getText().toString().equals(getResources().getString(R.string.submit))) {
            String userInput = Constants.getBreedNames().get(breedSpinner.getSelectedItemPosition());
            if (userInput.equals(generatedBreed)) {
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
