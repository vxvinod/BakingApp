package com.example.a60010743.bakingpro.Utilities;

import com.example.a60010743.bakingpro.model.RecepieDetails;
import com.example.a60010743.bakingpro.model.RecepieIngredients;
import com.example.a60010743.bakingpro.model.RecepieStepDetails;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public  class JsonParseUtils {

    // Parse RecepieData in to RecepieDetails structure.
    public static List<RecepieDetails> parseRecepieData(String recepieData) throws JSONException {
        final String NAME = "name";
        final String INGREDIENTS = "ingredients";
        final String STEPS = "steps";
        final String SERVINGS = "servings";
        final String IMAGE = "image";

        List<RecepieDetails> recepieCollection = new ArrayList<RecepieDetails>();
        JSONArray jsonData = new JSONArray(recepieData);

        if(jsonData == null) return null;

        for(int i = 0; i < jsonData.length(); i++) {
            JSONObject data = jsonData.getJSONObject(i);
            recepieCollection.add(new RecepieDetails(data.optString(NAME),
                                    data.optString(INGREDIENTS), data.optString(STEPS),
                                    data.optString(SERVINGS), data.optString(IMAGE)));
        }
        return recepieCollection;
    }

    // Parse RecepieData to RecepieIngredients Structure
    public static List<RecepieIngredients> parseIngData(String ingData) throws JSONException {
        final String QUANTITY = "quantity";
        final String MEASURE = "measure";
        final String INGREDIENT = "ingredient";

        List<RecepieIngredients> recepieIngredients = new ArrayList<>();
        JSONArray jsonData = new JSONArray(ingData);

        if(jsonData == null) return null;
        for(int i = 0; i < jsonData.length(); i++) {
            JSONObject data = jsonData.getJSONObject(i);
            recepieIngredients.add(new RecepieIngredients(data.optString(INGREDIENT),
                                        data.optString(QUANTITY), data.optString(MEASURE)));
        }
        return recepieIngredients;
    }
    // Parse RecepieData to RecepieStepDetails Structure.
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
            recepieStepDetails.add(new RecepieStepDetails(data.optString(SHORTDESC),
                                data.optString(DESC), data.optString(VIDEOURL),
                                data.optString(THUMBNAILURL)));
        }
        return recepieStepDetails;
    }
}
