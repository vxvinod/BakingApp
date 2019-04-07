package com.example.a60010743.bakingpro.Utilities;

import android.util.Log;

import com.example.a60010743.bakingpro.model.RecepieDetails;

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
}
