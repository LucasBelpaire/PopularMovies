package com.belpaire.lucas.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.belpaire.lucas.popularmovies.utilities.OpenMovieDataJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private static final String MOVIE_SHARE_HASHTAG = " #PopularMoviesApp";

    private String mMovieJson;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView mMovieReleaseDate = findViewById(R.id.release_date);

        TextView mMovieAverageScore = findViewById(R.id.vote_average);

        TextView mMovieSynopsis = findViewById(R.id.plot_synopsis);

        ImageView mMoviePoster = findViewById(R.id.iv_movie_poster);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity != null){
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
                mMovieJson = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            }
            String movieTitle = null;
            String movieReleaseDate = null;
            String moviePoster = null;
            String movieAverageScore = null;
            String movieSynopsis = null;
            try {
                moviePoster = OpenMovieDataJsonUtils.getMoviePosterUrl(mMovieJson);
                movieTitle = OpenMovieDataJsonUtils.getTitleOfJsonMovie(mMovieJson);
                movieReleaseDate = OpenMovieDataJsonUtils.getMovieReleaseDate(mMovieJson);
                movieAverageScore = OpenMovieDataJsonUtils.getMovieAverageScore(mMovieJson);
                movieSynopsis = OpenMovieDataJsonUtils.getMovieSynopsis(mMovieJson);
            } catch (JSONException e){
                e.printStackTrace();
            }
            setTitle(movieTitle);
            mMovieReleaseDate.setText(movieReleaseDate);
            mMovieAverageScore.setText(movieAverageScore);
            mMovieSynopsis.setText(movieSynopsis);
            Picasso.get().load(moviePoster).into(mMoviePoster);

        }
    }

    private Intent createShareMovieIntent() {
        return ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mMovieJson + MOVIE_SHARE_HASHTAG)
                .getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareMovieIntent());
        return true;
    }

}
