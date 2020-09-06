package com.cormacx.electroluxexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cormacx.electroluxexam.network.Movie;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private List<Movie> mValues;
    private OnMovieListener mOnMovieListener;

    public MovieRecyclerViewAdapter(List<Movie> items, OnMovieListener onMovieListener) {
        this.mValues = items;
        this.mOnMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item, parent, false);
        return new ViewHolder(view, mOnMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item = mValues.get(position);
        holder.idView.setText(mValues.get(position).id);
        holder.titleView.setText(mValues.get(position).title);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View view;
        public final ImageView moviePosterView;
        public final TextView idView;
        public final TextView titleView;
        public Movie item;
        OnMovieListener movieListener;

        public ViewHolder(View view, OnMovieListener onMovieListener) {
            super(view);
            this.view = view;
            idView = (TextView) view.findViewById(R.id.movie_id);
            moviePosterView = (ImageView) view.findViewById(R.id.movie_poster_image);
            titleView = (TextView) view.findViewById(R.id.movie_title);
            this.movieListener = onMovieListener;

            Glide.with(view).load("https://image.tmdb.org/t/p/w185".concat(item.poster_path)).into(moviePosterView);

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            movieListener.OnMovieClick(view, mValues.get(getAdapterPosition()).id);
        }
    }

    public interface OnMovieListener {
        void OnMovieClick(View view, String movieId);
    }

}
