package com.belpaire.lucas.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.belpaire.lucas.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static final String TOP_RATED = "top_rated";

    private static final String MOST_POPULAR = "popular";

    private static final String LANGUAGE_PARAM = "language";

    private static final String PAGE_PARAM = "page";

    private static final String API_KEY_PARAM = "api_key";

    private static final String english = "en-US";

    private static final String numPages = "1";

    public static URL buildUrl(String sortBy){
        String baseUrl;
        if(sortBy.equals("TOP_RATED")){
            baseUrl = BASE_URL + TOP_RATED;
        } else {
            baseUrl = BASE_URL + MOST_POPULAR;
        }
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, english)
                .appendQueryParameter(PAGE_PARAM, numPages)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpsUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
