package com.example.moviepedia.tmdb

import com.google.gson.annotations.SerializedName

data class TVShow(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val original_name: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("first_air_date") val first_air_date: String,
    @SerializedName("origin_country") val origin_country: List<String>,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("vote_count") val vote_count: Int
)