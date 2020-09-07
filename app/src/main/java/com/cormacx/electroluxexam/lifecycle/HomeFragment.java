package com.cormacx.electroluxexam.lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cormacx.electroluxexam.MovieRecyclerViewAdapter;
import com.cormacx.electroluxexam.SpacingDecoration;
import com.cormacx.electroluxexam.network.MovieServiceAPI;
import com.cormacx.electroluxexam.R;
import com.cormacx.electroluxexam.network.GetUpcomingMoviesResponse;
import com.cormacx.electroluxexam.network.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cormacx.electroluxexam.AppConstants.API_KEY;
import static com.cormacx.electroluxexam.AppConstants.BASE_URL;

public class HomeFragment extends Fragment implements MovieRecyclerViewAdapter.OnMovieListener {

    private static final String TAG = "HomeFragment";

    private static Retrofit retrofit = null;

    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter mAdapter;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    private void initializeRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.movie_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpacingDecoration deco = new SpacingDecoration(30);
        mRecyclerView.addItemDecoration(deco);
        mAdapter = new MovieRecyclerViewAdapter(new ArrayList<Movie>(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void OnMovieClick(View view, String movieId) {
        Log.d(TAG, "OnMovieClick: clicked!");
        Bundle bundle = new Bundle();
        bundle.putString("movieId", movieId);
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_viewMovieDetailsFragment, bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView(view);
        connectAndGetApiData();
    }

    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        MovieServiceAPI movieServiceAPI = retrofit.create(MovieServiceAPI.class);
        Call<GetUpcomingMoviesResponse> call = movieServiceAPI.listUpcomingMovies(API_KEY, "BR", "pt-BR");

        call.enqueue(new Callback<GetUpcomingMoviesResponse>() {
            @Override
            public void onResponse(Call<GetUpcomingMoviesResponse> call, Response<GetUpcomingMoviesResponse> response) {
                List<Movie> movieList = response.body().getResults();
                mAdapter.setValues(movieList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetUpcomingMoviesResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: call failed", t);
            }
        });
    }
}