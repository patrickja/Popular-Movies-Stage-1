package com.example.android.popularmovieappstageone;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieActivityFragment extends Fragment implements MovieAdapter.ListItemOnClickHandler {

    @BindView(R.id.rv_movie_display)
    RecyclerView mMovieDisplayRecyclerView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadProgressBar;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageTextView;

    private boolean mSavedInstanceStateFlag;
    private String mUserSortChoice;
    private ArrayList<Movie> mMovieDataList;
    private Context mMovieActivityContext;
    private Unbinder unbinder;
    RecyclerView.LayoutManager layoutManager;
    MovieAdapter mMovieAdapter;

    public void setProgressBarVisibility(int pbVisibility) {
        mLoadProgressBar.setVisibility(pbVisibility);
    }

    public void setMovieAdapter() {
        mMovieAdapter = new MovieAdapter(getActivity(), MovieActivityFragment.this, mMovieDataList);
        mMovieDisplayRecyclerView.setAdapter(mMovieAdapter);
    }

    public void setUserSortChoice(String userSortChoice) {
        this.mUserSortChoice = userSortChoice;
    }

    public void setSavedInstanceStateFlag(boolean savedInstanceStateFlag) {
        this.mSavedInstanceStateFlag = savedInstanceStateFlag;
    }

    public MovieActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(Constants.PARCELABLE_SAVE_STATE_KEY)) {
            setSavedInstanceStateFlag(false);
        } else {
            setSavedInstanceStateFlag(true);
            mMovieDataList = savedInstanceState.getParcelableArrayList(Constants.PARCELABLE_SAVE_STATE_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!(mMovieDataList == null || mMovieDataList.isEmpty()))
            outState.putParcelableArrayList(Constants.PARCELABLE_SAVE_STATE_KEY, mMovieDataList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        Boolean shouldAttachToParentImmediately = false;
        View movieFragmentView = layoutInflater.inflate(R.layout.fragment_movie, viewGroup, shouldAttachToParentImmediately);

        unbinder = ButterKnife.bind(this, movieFragmentView);

        showMovieDataView();
        setProgressBarVisibility(View.INVISIBLE);

        layoutManager = new GridLayoutManager(mMovieActivityContext, Constants.GRID_COLUMN_COUNT);
        mMovieDisplayRecyclerView.setLayoutManager(layoutManager);
        mMovieDisplayRecyclerView.setHasFixedSize(true);

        loadMovieData();

        return movieFragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mMovieActivityContext = context;
    }

    public void loadMovieData() {
        showMovieDataView();
        new FetchMovieTask(this).execute(mUserSortChoice);
    }

    public void showErrorMessageView() {
        mMovieDisplayRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    public void showMovieDataView() {
        mMovieDisplayRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    public void setMovieData(Movie[] movieData) {
        if (!mSavedInstanceStateFlag)
            mMovieDataList = new ArrayList<>(Arrays.asList(movieData));
        setSavedInstanceStateFlag(false);
    }

    @Override
    public void onClick(Movie movieClicked) {
        Class detailActivity = MovieDetailActivity.class;
        Intent movieDetailIntent = new Intent(mMovieActivityContext, detailActivity);
        movieDetailIntent.putExtra(Constants.PARCELABLE_EXTRA_KEY, movieClicked);
        startActivity(movieDetailIntent);
    }
}