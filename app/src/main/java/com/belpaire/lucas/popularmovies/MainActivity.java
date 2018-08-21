package com.belpaire.lucas.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.belpaire.lucas.popularmovies.utilities.NetworkUtils;
import com.belpaire.lucas.popularmovies.utilities.OpenMovieDataJsonUtils;

import java.net.URL;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);

        mRecyclerView.setLayoutManager(layoutManager);

        //improves performance, only use if you know that the size of the content never changes.
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData();
    }

    private void loadMovieData(){
        showMovieDataView();

        new FetchMovieTask().execute();
    }

    private void showMovieDataView(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String movieTitle){
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieTitle);
        startActivity(intentToStartDetailActivity);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params){

            URL movieDataRequestUrl = NetworkUtils.buildUrl();

            try {
                String jsonMovieDataResponse = NetworkUtils.getResponseFromHttpsUrl(movieDataRequestUrl);

                String[] movieListAsJsonObjects = OpenMovieDataJsonUtils.getMovieListAsJsonObjects(MainActivity.this, jsonMovieDataResponse);

                return movieListAsJsonObjects;

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieData){
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null){
                showMovieDataView();
                mMovieAdapter.setMovieTitleData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }
}
