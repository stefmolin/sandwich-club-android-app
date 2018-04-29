package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        /** Parse description of a sandwich as JSON and return a Sandwich object without using
         * any 3rd party libraries.
         *
         * @param json String containing the JSON of the information about the sandwich
         *
         * @return Sandwich object
         */

        /* Sample JSON:
         {\"name\":{\"mainName\":\"Medianoche\",\"alsoKnownAs\":[\"Cuban
         Sandwich\"]},\"placeOfOrigin\":\"Cuba\",\"description\":\"Medianoche (\\\"midnight\\\"
         in Spanish) is a type of sandwich which originated in Cuba. It is served in many Cuban
         communities in the United States. It is so named because of the sandwich\'s popularity
         asa staple served in Havana\'s night clubs right around or after
         midnight.\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Sandwich_de_Medianoche.jpg/800px-Sandwich_de_Medianoche.jpg\",\"ingredients\":[\"Egg
         bread\",\"Roast pork\",\"Ham\",\"Mustard\",\"Swiss cheese\",\"Dill pickles\"]}

         Sandwich object:
         Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin,
                  String description, String image, List<String> ingredients)
         */

        try {
            JSONObject root = new JSONObject(json);
            JSONObject name = root.getJSONObject("name");
            String sandwichName = name.getString("mainName");
            JSONArray akas = name.getJSONArray("alsoKnownAs");
            String origin = root.getString("placeOfOrigin");
            String description = root.getString("description");
            String image = root.getString("image");
            JSONArray ingredients = root.getJSONArray("ingredients");

            return new Sandwich(handleMissingInfo(sandwichName, "Missing sandwich name!"),
                                JsonUtils.jsonArrayToArrayList(akas, "N/A"),
                                handleMissingInfo(origin, "Unknown"),
                                handleMissingInfo(description, "No description found."),
                                image,
                                JsonUtils.jsonArrayToArrayList(ingredients, "Missing ingredients!"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String handleMissingInfo(String data, String fallbackValue) {
        if (data == null || data.equals("")) {
            return fallbackValue;
        } else {
            return data;
        }
    }

    private static ArrayList<String> jsonArrayToArrayList(JSONArray array, String emptyValue) {
        ArrayList<String> result = new ArrayList<>();

        if (array.length() != 0) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    result.add(array.getString(i));
                } catch (JSONException e) {
                    result.add(emptyValue);
                }
            }
        } else {
            result.add(emptyValue);
        }
        return result;
    }

    public static String arrayToString(List<String> array){
        String stringRepresentation = "";
        for(int i = 0; i < array.size(); i++){
            stringRepresentation += array.get(i);
            if(i < array.size() - 1){
                stringRepresentation += ", ";
            }
        }
        return stringRepresentation;
    }
}
