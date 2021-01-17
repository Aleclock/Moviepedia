package com.example.moviepedia.tmdb

import com.example.moviepedia.model.GenreTMDB
import com.example.moviepedia.model.ProductionCompanyTMDB
import com.example.moviepedia.model.ProductionCountryTMDB
import com.example.moviepedia.model.SeasonsTMDB
import com.google.gson.annotations.SerializedName

data class GetTVShowDetailResponse (
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("episode_run_time") val episode_run_time: List<Int>,
    @SerializedName("first_air_date") val first_air_date: String,
    @SerializedName("genres") val genres: List<GenreTMDB>,
    @SerializedName("homepage") val homepage : String,
    @SerializedName("id") val id : Int,
    @SerializedName("in_production") val in_production : Boolean,
    @SerializedName("languages") val languages : List<String>,
    @SerializedName("last_air_date") val last_air_date : String,
    @SerializedName("name") val name : String,
    @SerializedName("number_of_episodes") val number_of_episodes : Int,
    @SerializedName("number_of_seasons") val number_of_seasons : Int,
    @SerializedName("origin_country") val origin_country : List<String>,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("original_name") val original_name : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("popularity") val popularity : Float,
    @SerializedName("poster_path") val poster_path : String?,
    @SerializedName("production_companies") val production_companies: List<ProductionCompanyTMDB>,
    @SerializedName("production_countries") val production_countries: List<ProductionCountryTMDB>,
    @SerializedName("status") val status : String,
    @SerializedName("tagline") val tagline : String,
    @SerializedName("type") val type : String,
    @SerializedName("vote_average") val vote_average : Float,
    @SerializedName("vote_count") val vote_count : Int,
    @SerializedName("seasons") val seasons : List<SeasonsTMDB>
)

/* TODO
created by
last_episode_to_air
networks
spoken_languages
 */