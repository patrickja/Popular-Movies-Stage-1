package com.example.android.popularmovieappstageone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private final ListItemOnClickHandler mClickHandler;
    private ArrayList<Movie> mMovieDataList;
    Context mMovieFragmentActivityContext;

    public MovieAdapter(Activity movieActivity, ListItemOnClickHandler handler, ArrayList<Movie> movieList) {
        mMovieFragmentActivityContext = movieActivity;
        mClickHandler = handler;
        mMovieDataList = movieList;
    }

    public interface ListItemOnClickHandler {
        void onClick(Movie movieClicked);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mMoviePosterImage;

        public MovieViewHolder(View view) {
            super(view);
            mMoviePosterImage = view.findViewById(R.id.img_movie_poster);
            showMoviePoster();
            view.setOnClickListener(this);
        }

        public void showMoviePoster() {
            mMoviePosterImage.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movieDataForThisView = mMovieDataList.get(adapterPosition);
            mClickHandler.onClick(movieDataForThisView);
        }
    }

    @Override
    public int getItemCount() {
        if (mMovieDataList == null || mMovieDataList.isEmpty())
            return 0;
        else
            return mMovieDataList.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int LayoutIDForView = R.layout.movie_view;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater movieViewLayoutInflater = LayoutInflater.from(context);
        View movieView = movieViewLayoutInflater.inflate(LayoutIDForView, viewGroup, shouldAttachToParentImmediately);
        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        Movie movieDataForThisView = mMovieDataList.get(position);
        String posterURIString = movieDataForThisView.getPosterURLString();
        Picasso.get().load(posterURIString).placeholder(R.drawable.loading).error(R.drawable.failed).into(movieViewHolder.mMoviePosterImage);
    }
}