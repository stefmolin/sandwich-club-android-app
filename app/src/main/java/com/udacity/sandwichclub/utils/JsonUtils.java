package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

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
        json = json.replace("\"", "").replace("\\", "");

        /* This now looks like (spacing is for ease of understanding only)
        {
           name:{
                 mainName:Medianoche,
                 alsoKnownAs:[Cuban Sandwich]
                 },
           placeOfOrigin:Cuba,
           description:Medianoche (midnight in Spanish) is a type of sandwich which originated in Cuba.
                       It is served in many Cuban communities in the United States.
                       It is so named because of the sandwich's popularity as a staple served in Havana's
                       night clubs right around or after midnight.,
           image:https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Sandwich_de_Medianoche.jpg/800px-Sandwich_de_Medianoche.jpg,
           ingredients:[Egg bread,Roast pork,Ham,Mustard,Swiss cheese,Dill pickles]
         }
        */

        String jsonArray[] = json.split("\\{");

        // Parse sandwich name
        Pattern namePattern = Pattern.compile("(?:(mainName:))(\\w*)");
        Matcher nameMatcher = namePattern.matcher(jsonArray[2]);
        String sandwichName;
        if (nameMatcher.find()) {
            sandwichName = nameMatcher.group(2);
        } else {
            sandwichName = "";
        }

        // Parse sandwich AKA's
        Pattern akaPattern = Pattern.compile("(?:(alsoKnownAs:))(([^\\}])*)");
        Matcher akaMatcher = akaPattern.matcher(jsonArray[2]);
        String[] akaNames;
        if (akaMatcher.find()) {
            akaNames = akaMatcher.group(2).replaceAll("[\\[\\]]", "").split(",");
        } else {
            akaNames = new String[1];
            akaNames[0] = "N/A";
        }
        ArrayList<String> akas = new ArrayList<>();
        for (String aka : akaNames) {
            if (aka != null && aka != ""){
                akas.add(aka);
            } else {
                akas.add("N/A");
            }
        }

        // Parse sandwich origin
        Pattern originPattern = Pattern.compile("(?:(placeOfOrigin:))(.*)(?=,description)");
        Matcher originMatcher = originPattern.matcher(jsonArray[2]);
        String origin;
        if (originMatcher.find()) {
            origin = originMatcher.group(2);
            if (origin == null || origin == "") {
                origin = "Unknown";
            }
        } else {
            origin = "Unknown";
        }

        // Parse sandwich description
        Pattern descriptionPattern = Pattern.compile("(?:(description:))(.*)(?=,image)");
        Matcher descriptionMatcher = descriptionPattern.matcher(jsonArray[2]);
        String description;
        if (descriptionMatcher.find()) {
            description = descriptionMatcher.group(2);
        } else {
            description = "Description missing.";
        }

        // Parse sandwich image
        Pattern imagePattern = Pattern.compile("(?:(image:))(.*)(?:,)(?=ingredients)");
        Matcher imageMatcher = imagePattern.matcher(jsonArray[2]);
        String image;
        if (imageMatcher.find()) {
            image = imageMatcher.group(2);
        } else {
            image = "";
        }

        // Parse sandwich ingredients
        Pattern ingredientsPattern = Pattern.compile("(?:(ingredients:))(([^\\}])*)");
        Matcher ingredientsMatcher = ingredientsPattern.matcher(jsonArray[2]);
        String[] ingredients;
        if (ingredientsMatcher.find()) {
            ingredients = ingredientsMatcher.group(2).replaceAll("[\\[\\]]", "").split(",");
        } else {
            ingredients = new String[1];
            ingredients[0] = "Ingredients missing!";
        }
        ArrayList<String> ingredientsArrayList = new ArrayList<>();
        for (String ingredient : ingredients) {
            ingredientsArrayList.add(ingredient);
        }
        return new Sandwich(sandwichName, akas, origin, description, image, ingredientsArrayList);
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
