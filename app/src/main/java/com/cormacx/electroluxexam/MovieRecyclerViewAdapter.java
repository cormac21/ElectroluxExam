package com.cormacx.electroluxexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cormacx.electroluxexam.network.Movie;

import org.w3c.dom.Text;

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
        holder.bindViewHolder(mValues.get(position));
    }

    public void setValues(List<Movie> mValues) {
        this.mValues = mValues;
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
        public final TextView releaseDateView;
        public Movie item;
        OnMovieListener movieListener;

        public ViewHolder(View view, OnMovieListener onMovieListener) {
            super(view);
            this.view = view;
            idView = (TextView) view.findViewById(R.id.movie_id);
            moviePosterView = (ImageView) view.findViewById(R.id.movie_poster_image);
            titleView = (TextView) view.findViewById(R.id.movie_title);
            releaseDateView = (TextView) view.findViewById(R.id.movie_release_date);
            this.movieListener = onMovieListener;
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            Integer position = getAdapterPosition();
            movieListener.OnMovieClick(view, mValues.get(position).id);
        }

        public void bindViewHolder(Movie movie) {
            idView.setText(movie.id);
            titleView.setText(movie.title);
            releaseDateView.setText("Nos cinemas em: ".concat(movie.release_date));
            if( movie.backdrop_path != null ) {
                Glide.with(view).load("https://image.tmdb.org/t/p/w185".concat(movie.backdrop_path)).into(moviePosterView);
            } else {
                Glide.with(view).load(R.mipmap.shrug).into(moviePosterView);
            }
        }
    }

    public interface OnMovieListener {
        void OnMovieClick(View view, String movieId);
    }

}
