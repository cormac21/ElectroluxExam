package com.cormacx.electroluxexam.lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cormacx.electroluxexam.R;
import com.cormacx.electroluxexam.network.GetMovieDetailsResponse;
import com.cormacx.electroluxexam.network.MovieServiceAPI;

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

    private TextView mMovieTitle, mMovieOverview, mMovieReleaseDate, mMovieStatus, mMovieRuntime;
    private ImageView mMovieImage;

    public ViewMovieDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_movie_details, container, false);
        mMovieImage = (ImageView) view.findViewById(R.id.details_image);
        mMovieTitle = (TextView) view.findViewById(R.id.details_title);
        mMovieOverview = (TextView) view.findViewById(R.id.details_overview);
        mMovieReleaseDate = (TextView) view.findViewById(R.id.details_release_date);
        mMovieStatus = (TextView) view.findViewById(R.id.details_status);
        mMovieRuntime = (TextView) view.findViewById(R.id.details_runtime);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String movieId = getArguments().getString("movieId");
        connectAndGetData(movieId);
    }

    private void connectAndGetData(String movieId ) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        MovieServiceAPI movieServiceAPI = retrofit.create(MovieServiceAPI.class);

        Call<GetMovieDetailsResponse> call = movieServiceAPI.getMovieDetails(movieId, API_KEY, "BR", "pt-BR");

        call.enqueue(new Callback<GetMovieDetailsResponse>() {
            @Override
            public void onResponse(Call<GetMovieDetailsResponse> call, Response<GetMovieDetailsResponse> response) {
                GetMovieDetailsResponse movieDetails = response.body();
                if ( movieDetails.getPoster_path() != null ) {
                    Glide.with(getActivity().getApplicationContext()).load("https://image.tmdb.org/t/p/w185".concat(movieDetails.getPoster_path())).into(mMovieImage);
                } else {
                    Glide.with(getActivity().getApplicationContext()).load(R.mipmap.shrug).into(mMovieImage);
                }
                mMovieTitle.setText(movieDetails.getTitle());
                mMovieOverview.setText(movieDetails.getOverview());
                mMovieReleaseDate.setText(movieDetails.getRelease_date());
                mMovieRuntime.setText(movieDetails.getRuntime().concat(" minutos"));
                mMovieStatus.setText(movieDetails.getStatus());
            }

            @Override
            public void onFailure(Call<GetMovieDetailsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: Failed to get movie details from MovieAPI", t);
            }
        });
    }

}