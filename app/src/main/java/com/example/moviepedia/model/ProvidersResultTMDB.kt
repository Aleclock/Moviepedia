package com.example.moviepedia.model

import com.google.gson.annotations.SerializedName

/**
 * https://developers.themoviedb.org/3/tv/get-tv-watch-providers
 */
data class ProvidersResultTMDB (
        @SerializedName("DE") val DE: MovieProvidersTMDB,
        @SerializedName("ES") val ES: MovieProvidersTMDB,
        @SerializedName("FR") val FR: MovieProvidersTMDB,
        @SerializedName("GB") val GB: MovieProvidersTMDB,
        @SerializedName("IT") val IT: MovieProvidersTMDB,
        @SerializedName("JP") val JP: MovieProvidersTMDB,
        @SerializedName("RU") val RU: MovieProvidersTMDB,
        @SerializedName("US") val US: MovieProvidersTMDB
)