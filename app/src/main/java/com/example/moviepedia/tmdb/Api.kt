package com.example.moviepedia.tmdb

import com.example.moviepedia.BuildConfig.TMDB_key
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = TMDB_key,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = TMDB_key,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = TMDB_key,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/latest")
    fun getLatestMovies(
        @Query("api_key") apiKey: String = TMDB_key
    ): Call<GetMoviesResponse>

    @GET("tv/popular")
    fun getPopularTVShow(
        @Query("api_key") apiKey: String = TMDB_key,
        @Query("page") page: Int
    ): Call<GetTVShowResponse>

    @GET("tv/top_rated")
    fun getTopRatedTVShow(
        @Query("api_key") apiKey: String = TMDB_key,
        @Query("page") page: Int
    ): Call<GetTVShowResponse>

    @GET("tv/latest")
    fun getLatestTVShow(
        @Query("api_key") apiKey: String = TMDB_key
    ): Call<GetTVShowResponse>
}