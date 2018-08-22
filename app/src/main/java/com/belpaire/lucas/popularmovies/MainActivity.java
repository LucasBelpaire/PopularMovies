package com.belpaire.lucas.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private boolean sortByTopRated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(layoutManager);

        //improves performance, only use if you know that the size of the content never changes.
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movies, menu);
        return true;
    }

    private void loadMovieData(){
        showMovieDataView();

        String sortBy = "TOP_RATED";
        if(!sortByTopRated){
            sortBy = "MOST_POPULAR";
        }
        new FetchMovieTask().execute(sortBy);
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

            String sortBy = "TOP_RATED";

            if(params.length > 0){
                sortBy = params[0];
            }

            URL movieDataRequestUrl = NetworkUtils.buildUrl(sortBy);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.action_refresh:
                loadMovieData();
                return true;
            case R.id.action_sort_by_top_rated:
                if(!menuItem.isChecked()){
                    menuItem.setChecked(true);
                    sortByTopRated = true;
                    loadMovieData();
                }
                return true;
            case R.id.action_sort_by_most_popular:
                if(!menuItem.isChecked()){
                    menuItem.setChecked(true);
                    sortByTopRated = false;
                    loadMovieData();
                }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
