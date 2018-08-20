package com.belpaire.lucas.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private String[] mMovieTitleData;

    private final MovieAdapterClickHandler mClickHandler;

    public interface MovieAdapterClickHandler {
        void onClick(String movieTitle);
    }

    public MovieAdapter(MovieAdapterClickHandler clickHandler){
        mClickHandler = clickHandler;
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mMovieTextView;

        public MovieAdapterViewHolder(View view){
            super(view);
            mMovieTextView = (TextView) view.findViewById(R.id.movie_title_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String movieTitle = mMovieTitleData[adapterPosition];
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
        String movieTitle = mMovieTitleData[position];
        movieAdapterViewHolder.mMovieTextView.setText(movieTitle);
    }

    @Override
    public int getItemCount(){
        if (null == mMovieTitleData){
            return 0;
        } else {
            return mMovieTitleData.length;
        }
    }

    public void setmMovieTitleData(String[] movieTitleData){
        mMovieTitleData = movieTitleData;
        notifyDataSetChanged();
    }
}
