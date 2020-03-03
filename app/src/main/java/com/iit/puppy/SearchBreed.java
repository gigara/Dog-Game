package com.iit.puppy;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import static com.iit.puppy.Constants.BREEDS;
import static com.iit.puppy.Constants.IMAGES;

public class SearchBreed extends AppCompatActivity {
    ImageView image;
    Handler mHandler = new Handler();
    TextView userInput;
    Button start;
    Button stop;
    boolean isSubmit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_breed);

        userInput = findViewById(R.id.userInput);
        image = findViewById(R.id.slideshowImage);
        start = findViewById(R.id.submitBreed);
        stop = findViewById(R.id.stopBreed);
    }

    Runnable mHandlerTask = new Runnable() {
        @Override
        public void run() {
            if (isSubmit) {
                loadRandomImage();
                mHandler.postDelayed(mHandlerTask, 5000);
            }
        }
    };

    private void loadRandomImage() {
        // load random breed
        Random generator = new Random();
        String breed = "";
        for (String s : BREEDS) {
            String userSearchQuery = userInput.getText().toString();
            if (!userSearchQuery.equals("") && s.toLowerCase().contains(userSearchQuery)) {
                breed = s;
                isSubmit = true;
                break;
            } else {
                breed = "";
                isSubmit = false;
            }
        }
        if (!breed.equals("")) {
            userInput.setEnabled(false);
            start.setEnabled(false);
            List<String> breedImages = IMAGES.get(breed);

            // load random image from the selected breed
            String img = "Images/" + breed + "/" + breedImages.get(generator.nextInt(breedImages.size()));

            // load image
            try (InputStream ims = getAssets().open(img)) {
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                image.setImageDrawable(d);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            mHandler.removeCallbacks(mHandlerTask);
        }
    }

    public void submit(View view) {
        isSubmit = true;
        mHandlerTask.run();
    }

    public void stop(View view) {
        userInput.setEnabled(true);
        start.setEnabled(true);
        mHandler.removeCallbacks(mHandlerTask);
    }
}
