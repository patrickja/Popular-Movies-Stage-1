package com.example.android.popularmovieappstageone;

import android.content.Context;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    public static URL buildPosterURL(String posterPath){
        Uri builtUri = Uri.parse(Constants.MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(Constants.MOVIE_POSTER_SIZE)
                .appendEncodedPath(posterPath).build();
        URL builtURL = null;

        try {
            builtURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return builtURL;
    }

    public static URL buildBackdropURL(String backdropPath){
        Uri builtUri = Uri.parse(Constants.MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(Constants.MOVIE_POSTER_SIZE)
                .appendPath(backdropPath).build();
        URL builtURL = null;

        try {
            builtURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return builtURL;
    }

    public static URL buildMovieURL(Context activityContext) {
        return buildMovieURL(Constants.MOVIE_POPULAR, activityContext);
    }

    public static URL buildMovieURL(String userChoice, Context activityContext) {
        String apiKey = BuildConfig.THE_MOVIE_DB_API_TOKEN;
        Uri builtUri = Uri.parse(Constants.MOVIE_BASE_URL).buildUpon().appendPath(userChoice).appendQueryParameter(Constants.API_PARAM_NAME, apiKey).build();
        URL builtURL = null;

        try {
            builtURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return builtURL;
    }

    public static String getResponseFromHttpURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner readScanner = new Scanner(inputStream);
            readScanner.useDelimiter("\\A");

            boolean hasInput = readScanner.hasNext();
            if (hasInput)
                return readScanner.next();
            else
                return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}