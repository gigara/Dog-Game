package com.iit.puppy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Constants {
    static ArrayList<String> BREEDS = new ArrayList<>();
    static HashMap<String, List<String>> IMAGES = new HashMap<>();

    /**
     * get all breed names as a ArrayList
     * @return list of breed names
     */
    static ArrayList<String> getBreedNames() {
        ArrayList<String> breeds = new ArrayList<>();
        for (String breed : BREEDS) {
            StringBuilder builder = new StringBuilder();
            for(String s : breed.split("-", 1)) {
                builder.append(s);
            }
            breeds.add(builder.toString());
        }
        return breeds;
    }
}
