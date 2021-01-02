package com.example.moviepedia.model

import com.google.gson.annotations.SerializedName

class MovieProvidersTMDB (
        @SerializedName("link") val link: String,
        @SerializedName("buy") val buy: List<ProviderTMDB>,
        @SerializedName("flatrate") val flatrate: List<ProviderTMDB>,
        @SerializedName("rent") val rent: List<ProviderTMDB>
)