package com.example.moviepedia.model

import com.google.gson.annotations.SerializedName

data class MovieTMDB (
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("adult") val adult: Boolean
)