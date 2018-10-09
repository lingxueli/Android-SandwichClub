package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);

        placeOfOrigin.setText(handleMissing(sandwich.getPlaceOfOrigin()));
        description.setText(handleMissing(sandwich.getDescription()));

        List<String> alias = sandwich.getAlsoKnownAs();
        StringBuilder result_alias = new StringBuilder("");
        for (String s: alias) {
            result_alias.append(s);
            result_alias.append(", ");
        }
        System.out.print(result_alias);
        alsoKnownAs.setText(handleMissing(new String(result_alias)));

        List<String> list_ingredient = sandwich.getIngredients();
        StringBuilder result_ingredients = new StringBuilder("");
        for (String s: list_ingredient) {
            result_ingredients.append(s);
            result_ingredients.append(", ");
        }
        ingredients.setText(handleMissing(new String(result_ingredients)));

    }

    private String handleMissing(String s){
        if (s.equals("")){
            return getString(R.string.error_data_missing);
        } else {
            return s;
        }
    }
}
