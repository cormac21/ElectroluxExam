package com.cormacx.electroluxexam.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieServiceAPI {

    @GET("movie/upcoming")
    Call<GetUpcomingMoviesResponse> listUpcomingMovies(@Query("api_key") String apiKey, @Query("region") String region, @Query("language") String language);

    @GET("movie/{movie_id}")
    Call<GetMovieDetailsResponse> getMovieDetails(@Path("movie_id") String movie_id,
                                                  @Query("api_key") String apiKey, @Query("region") String region, @Query("language") String language);

}
