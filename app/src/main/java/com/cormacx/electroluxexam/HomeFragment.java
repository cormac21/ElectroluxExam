package com.cormacx.electroluxexam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements MovieRecyclerViewAdapter.OnMovieListener {

    private static final String TAG = "HomeFragment";

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY = "b0c73ad07cee89803fbb29675ce01190";

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

        Button goToMovieDetailsButton = (Button) view.findViewById(R.id.go_to_movie_details_button);
        goToMovieDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnMovieClick(view, "577922");
            }
        });
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
                mAdapter = new MovieRecyclerViewAdapter(movieList, (MovieRecyclerViewAdapter.OnMovieListener) getParentFragmentManager().findFragmentById(R.id.homeFragment));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetUpcomingMoviesResponse> call, Throwable t) {


            }
        });
    }
}