package com.belpaire.lucas.popularmovies.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class OpenMovieDataJsonUtils {

    public static String[] getMovieListAsJsonObjects(Context context, String jsonMovieDataStr) throws JSONException{

        final String MOVIE_LIST = "results";

        String[] movieListAsJsonObjects = null;

        JSONObject movieJson = new JSONObject(jsonMovieDataStr);

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_LIST);

        movieListAsJsonObjects = new String[movieArray.length()];

        for(int i = 0; i < movieArray.length(); i++){
            movieListAsJsonObjects[i] =  movieArray.getJSONObject(i).toString();
        }

        return movieListAsJsonObjects;
    }

    public static JSONObject[] getMoviesListAsJsonObjects(Context context, String jsonMovieDataStr) throws JSONException {

        final String MOVIE_LIST = "results";

        JSONObject newJsonMovieData = new JSONObject(jsonMovieDataStr);

        JSONArray newJsonMovieDataArray = newJsonMovieData.getJSONArray(MOVIE_LIST);

        JSONObject[] listOfMoviesAsJson = new JSONObject[newJsonMovieDataArray.length()];

        for(int i = 0; i < newJsonMovieDataArray.length(); i++){
            listOfMoviesAsJson[i] = newJsonMovieDataArray.getJSONObject(i);
        }

        return listOfMoviesAsJson;

    }

    public static String getTitleOfJsonMovie(String jsonMovie) throws JSONException{

        final String TITLE = "title";

        JSONObject movie = new JSONObject(jsonMovie);

        return movie.getString(TITLE);

    }

    public static String getMoviePosterUrl(String jsonMovie) throws JSONException {

        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";

        final String NORMAL_POSTER_SIZE = "w342";

        final String POSTER_IMAGE = "poster_path";

        JSONObject movie = new JSONObject(jsonMovie);

        return BASE_IMAGE_URL + NORMAL_POSTER_SIZE + movie.getString(POSTER_IMAGE);

    }

    public static String getMovieReleaseDate(String jsonMovie) throws JSONException {
        final String RELEASE_DATE = "release_date";

        JSONObject movie = new JSONObject(jsonMovie);

        return movie.getString(RELEASE_DATE);
    }

    public static String getMovieAverageScore(String jsonMovie) throws JSONException {
        final String AVERAGE_SCORE = "vote_average";

        JSONObject movie = new JSONObject(jsonMovie);

        return movie.getString(AVERAGE_SCORE);
    }

    public static String getMovieSynopsis(String jsonMovie) throws JSONException {
        final String SYNOPSIS = "overview";

        JSONObject movie = new JSONObject(jsonMovie);

        return movie.getString(SYNOPSIS);
    }

}
