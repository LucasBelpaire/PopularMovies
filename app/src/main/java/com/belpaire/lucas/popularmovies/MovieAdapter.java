package com.belpaire.lucas.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.belpaire.lucas.popularmovies.utilities.OpenMovieDataJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private String[] mMovieJsonData;

    private final MovieAdapterClickHandler mClickHandler;

    public interface MovieAdapterClickHandler {
        void onClick(String movieTitle);
    }

    public MovieAdapter(MovieAdapterClickHandler clickHandler){
        mClickHandler = clickHandler;
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMovieImageView;

        public MovieAdapterViewHolder(View view){
            super(view);
            mMovieImageView = (ImageView) view.findViewById(R.id.movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            String movieJson = mMovieJsonData[adapterPosition];
            String movieTitle = null;
            try {
                movieTitle = OpenMovieDataJsonUtils.getTitleOfJsonMovie(movieJson);
            } catch (JSONException e){
                e.printStackTrace();
            }
            mClickHandler.onClick(movieTitle);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position){

        String movieJson = mMovieJsonData[position];
        String moviePoster = null;
        try {
            moviePoster = OpenMovieDataJsonUtils.getMoviePosterUrl(movieJson);
        } catch (JSONException e){
            e.printStackTrace();
        }
        Picasso.get().load(moviePoster).into(movieAdapterViewHolder.mMovieImageView);
    }

    @Override
    public int getItemCount(){
        if (null == mMovieJsonData){
            return 0;
        } else {
            return mMovieJsonData.length;
        }
    }

    public void setMovieTitleData(String[] movieTitleData){
        mMovieJsonData = movieTitleData;
        notifyDataSetChanged();
    }
}
