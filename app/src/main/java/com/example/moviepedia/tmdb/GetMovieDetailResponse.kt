package com.example.moviepedia.tmdb

import com.example.moviepedia.model.GenreTMDB
import com.google.gson.annotations.SerializedName

data class GetMovieDetailResponse (
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdb_id: String,
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<GenreTMDB>,
    @SerializedName("vote_average") val vote_average: Float,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("overview") val overview : String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("status") val status: String, // Allowed Values: Rumored, Planned, In Production, Post Production, Released, Canceled
    @SerializedName("tagline") val tagline: String,
    @SerializedName("video") val video: Boolean
)

/*
TODO
belongs_to_collection
production_companies
production_countries
spoken_languages
 */