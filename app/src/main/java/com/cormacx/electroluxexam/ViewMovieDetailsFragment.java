package com.cormacx.electroluxexam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewMovieDetailsFragment extends Fragment {

    public ViewMovieDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_movie_details, container, false);
        TextView textView = (TextView) view.findViewById(R.id.view_movie_details_text);
        textView.setText(getArguments().getString("movieId"));

        return view;
    }

}