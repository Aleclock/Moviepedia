package com.example.moviepedia.tmdb

import com.example.moviepedia.model.TVShowTMDB
import com.google.gson.annotations.SerializedName

data class GetTVShowResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val shows: List<TVShowTMDB>,
    @SerializedName("total_pages") val pages: Int
)