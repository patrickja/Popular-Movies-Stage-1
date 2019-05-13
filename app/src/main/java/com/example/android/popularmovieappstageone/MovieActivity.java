package com.example.android.popularmovieappstageone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MovieActivity extends AppCompatActivity {

    private MovieActivityFragment movieFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int movieActivityLayout = R.layout.activity_movie;
        setContentView(movieActivityLayout);

        movieFragment = (MovieActivityFragment) getFragmentManager().findFragmentById(R.id.fragment_movie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sub_menu_sort_popularity: movieFragment.setUserSortChoice(Constants.MOVIE_POPULAR);
                movieFragment.loadMovieData();
                break;
            case R.id.sub_menu_sort_top_rated:  movieFragment.setUserSortChoice(Constants.MOVIE_TOP_RATED);
                movieFragment.loadMovieData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}