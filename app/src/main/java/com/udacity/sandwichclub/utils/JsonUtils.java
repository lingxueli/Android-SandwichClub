package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import java.sql.Array;
import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject root = new JSONObject(json);
            JSONObject name = root.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            ArrayList<String> list_alsoKnownAs = new ArrayList<>();

            for(int i=0; i<alsoKnownAs.length(); i++){
                list_alsoKnownAs.add(alsoKnownAs.getString(i));
            }

            String placeOfOrigin = root.getString("placeOfOrigin");
            String description = root.getString("description");
            String image = root.getString("image");
            JSONArray ingredients = root.getJSONArray("ingredients");
            ArrayList<String> list_ingredients = new ArrayList<>();

            for(int i=0; i<ingredients.length(); i++){
                list_ingredients.add(ingredients.getString(i));
            }

            return new Sandwich(mainName, list_alsoKnownAs, placeOfOrigin, description, image, list_ingredients);
        }
        catch (JSONException e) {
                e.printStackTrace();
        }
        return null;

    }
}
