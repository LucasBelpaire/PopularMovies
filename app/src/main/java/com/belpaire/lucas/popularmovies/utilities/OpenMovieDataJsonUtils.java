package com.belpaire.lucas.popularmovies.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class OpenMovieDataJsonUtils {

    public static String[] getSimpleMovieDataStringsFromJson(Context context, String jsonMovieDataStr) throws JSONException{

        // TODO final strings used for getting things out of the json object

        final String MOVIE_LIST = "results";

        final String MOVIE_TITLE = "title";

        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(jsonMovieDataStr);

        // TODO add an error check

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_LIST);

        parsedMovieData = new String[movieArray.length()];

        for(int i = 0; i < movieArray.length(); i++){

            JSONObject movie = movieArray.getJSONObject(i);

            parsedMovieData[i] = movie.getString(MOVIE_TITLE);

        }

        return parsedMovieData;
    }
}
