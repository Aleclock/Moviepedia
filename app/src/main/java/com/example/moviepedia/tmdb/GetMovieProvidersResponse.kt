package com.example.moviepedia.tmdb

import com.example.moviepedia.model.ProvidersResultTMDB
import com.google.gson.annotations.SerializedName

data class GetMovieProvidersResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: ProvidersResultTMDB
)