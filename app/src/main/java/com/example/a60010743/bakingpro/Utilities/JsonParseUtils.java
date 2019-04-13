package com.example.a60010743.bakingpro.Utilities;

import android.util.Log;

import com.example.a60010743.bakingpro.model.RecepieDetails;
import com.example.a60010743.bakingpro.model.RecepieIngredients;
import com.example.a60010743.bakingpro.model.RecepieStepDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public  class JsonParseUtils {




    public static List<RecepieDetails> parseRecepieData(String recepieData) throws JSONException {
        final String NAME = "name";
        final String INGREDIENTS = "ingredients";
        final String STEPS = "steps";

        List<RecepieDetails> recepieCollection = new ArrayList<RecepieDetails>();
        JSONArray jsonData = new JSONArray(recepieData);
        //JSONObject recJsonData = new JSONObject(recepieData);

        if(jsonData == null) return null;
        for(int i = 0; i < jsonData.length(); i++) {
            JSONObject data = jsonData.getJSONObject(i);
            String name = data.optString(NAME);
            String ingredients = data.optString(INGREDIENTS);
            String steps = data.optString(STEPS);
            recepieCollection.add(new RecepieDetails(name, ingredients, steps));
        }
//        for(RecepieDetailsActivity r:recepieCollection) {
//            Log.d("RecepieData","Name---"+ r.getRecepieItem().toString());
//            Log.d("RecepieIng","Name---"+ r.getRecepieIng().toString());
//            Log.d("RecepieStepsActivity","Name---"+ r.getRecepieSteps().toString());
//        }
        return recepieCollection;
    }

    public static List<RecepieIngredients> parseIngData(String ingData) throws JSONException {
        final String QUANTITY = "quantity";
        final String MEASURE = "measure";
        final String INGREDIENT = "ingredient";

        List<RecepieIngredients> recepieIngredients = new ArrayList<>();
        JSONArray jsonData = new JSONArray(ingData);

        if(jsonData == null) return null;
        for(int i = 0; i < jsonData.length(); i++) {
            JSONObject data = jsonData.getJSONObject(i);
            String ingredient = data.optString(INGREDIENT);
            String quantity   = data.optString(QUANTITY);
            String measure    = data.optString(MEASURE);
            recepieIngredients.add(new RecepieIngredients(ingredient, quantity, measure));
        }
        return recepieIngredients;
    }

    public static List<RecepieStepDetails> parseRecSteps(String recStepsData) throws JSONException {
        final String SHORTDESC = "shortDescription";
        final String DESC = "description";
        final String VIDEOURL = "videoURL";
        final String THUMBNAILURL = "thumbnailURL";

        List<RecepieStepDetails> recepieStepDetails = new ArrayList<>();
        JSONArray jsonData = new JSONArray(recStepsData);

        if(jsonData == null) return null;
        for(int i = 0; i < jsonData.length(); i++) {
            JSONObject data = jsonData.getJSONObject(i);
            String shortDesc = data.optString(SHORTDESC);
            String desc   = data.optString(DESC);
            String videoUrl    = data.optString(VIDEOURL);
            String thumbnailUrl  = data.optString(THUMBNAILURL);
            recepieStepDetails.add(new RecepieStepDetails(shortDesc, desc, videoUrl, thumbnailUrl));
        }
        return recepieStepDetails;
    }
}
