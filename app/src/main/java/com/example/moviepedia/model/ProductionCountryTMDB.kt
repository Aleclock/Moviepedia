package com.example.moviepedia.model

import com.google.gson.annotations.SerializedName

data class ProductionCountryTMDB (
        @SerializedName("iso_3166_1") val iso_3166_1: String,
        @SerializedName("name") val name: String
)