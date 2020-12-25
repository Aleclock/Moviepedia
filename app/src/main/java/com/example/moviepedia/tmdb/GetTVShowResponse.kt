package com.example.moviepedia.tmdb

import com.google.gson.annotations.SerializedName

data class GetTVShowResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val shows: List<TVShow>,
    @SerializedName("total_pages") val pages: Int
)