package com.example.android.popularmovieappstageone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.img_thumbnail) ImageView mThumbnailImageView;
    @BindView(R.id.tv_movie_title) TextView mMovieTitleTextView;
    @BindView(R.id.tv_movie_overview) TextView mMovieOverviewTextView;
    @BindView(R.id.tv_movie_vote_average) TextView mMovieVoteAverageTextView;
    @BindView(R.id.tv_release_date) TextView mMovieReleaseDateTextView;

    Movie mMovieClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intentThatStartedThisActivity = getIntent();

        ButterKnife.bind(this);

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Constants.PARCELABLE_EXTRA_KEY)) {

                mMovieClicked = intentThatStartedThisActivity.getParcelableExtra(Constants.PARCELABLE_EXTRA_KEY);

                float floatVoteAverage = (float) mMovieClicked.getVoteAverage();

                Picasso.get().load(mMovieClicked.getPosterURLString()).placeholder(R.drawable.loading).error(R.drawable.failed).into(mThumbnailImageView);
                mMovieTitleTextView.setText(mMovieClicked.getOriginalTitle());
                mMovieOverviewTextView.setText(mMovieClicked.getOverview());
                mMovieVoteAverageTextView.setText(Float.toString(floatVoteAverage));
                mMovieReleaseDateTextView.setText(mMovieClicked.getReleaseDate());
            }
        }
    }
}