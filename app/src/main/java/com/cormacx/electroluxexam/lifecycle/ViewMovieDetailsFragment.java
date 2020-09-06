package com.cormacx.electroluxexam.lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cormacx.electroluxexam.R;
import com.cormacx.electroluxexam.network.GetMovieDetailsResponse;
import com.cormacx.electroluxexam.network.MovieServiceAPI;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cormacx.electroluxexam.AppConstants.API_KEY;
import static com.cormacx.electroluxexam.AppConstants.BASE_URL;

public class ViewMovieDetailsFragment extends Fragment {

    private static final String TAG = "ViewMovieDetailsFragmen";

    private static Retrofit retrofit = null;

    public ViewMovieDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_movie_details, container, false);
        TextView textView = (TextView) view.findViewById(R.id.view_movie_details_text);
        textView.setText(getArguments().getString("movieId"));

        connectAndGetData();

        return view;
    }

    private void connectAndGetData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        MovieServiceAPI movieServiceAPI = retrofit.create(MovieServiceAPI.class);

        Call<GetMovieDetailsResponse> call = movieServiceAPI.getMovieDetails("movieId", API_KEY, "BR", "pt-BR");

        call.enqueue(new Callback<GetMovieDetailsResponse>() {
            @Override
            public void onResponse(Call<GetMovieDetailsResponse> call, Response<GetMovieDetailsResponse> response) {
                GetMovieDetailsResponse movieDetails = response.body();

            }

            @Override
            public void onFailure(Call<GetMovieDetailsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Failed to get movie details from MovieAPI", t);
            }
        });
    }

}