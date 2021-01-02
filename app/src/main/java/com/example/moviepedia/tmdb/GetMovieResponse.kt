package com.example.moviepedia.tmdb

import com.example.moviepedia.model.MovieTMDB
import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieTMDB>,
    @SerializedName("total_results") val results: Int,
    @SerializedName("total_pages") val pages: Int
)