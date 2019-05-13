package com.example.android.popularmovieappstageone;

import android.os.AsyncTask;
import android.view.View;

import java.net.URL;

public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {

    private MovieActivityFragment mMovieActivityFragmentInstance;

    public FetchMovieTask(MovieActivityFragment movieActivityFragmentInstance) {
        this.mMovieActivityFragmentInstance = movieActivityFragmentInstance;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mMovieActivityFragmentInstance.setProgressBarVisibility(View.VISIBLE);
    }

    @Override
    protected Movie[] doInBackground(String... userSortChoice) {
        URL movieAPIURL;

        if (userSortChoice[0] == null)
            movieAPIURL = NetworkUtils.buildMovieURL(mMovieActivityFragmentInstance.getActivity());
        else
            movieAPIURL = NetworkUtils.buildMovieURL(userSortChoice[0], mMovieActivityFragmentInstance.getActivity());

        try {
            String movieJSONData = NetworkUtils.getResponseFromHttpURL(movieAPIURL);
            return OpenMovieJSONUtils.getMovieDataFromAPIJSONResult(movieJSONData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Movie[] movieData) {
        mMovieActivityFragmentInstance.setProgressBarVisibility(View.INVISIBLE);

        if (movieData != null) {
            mMovieActivityFragmentInstance.showMovieDataView();
            mMovieActivityFragmentInstance.setMovieData(movieData);
            mMovieActivityFragmentInstance.setMovieAdapter();
        } else {
            mMovieActivityFragmentInstance.showErrorMessageView();
        }
    }
}