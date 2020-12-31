package com.example.moviepedia.model

import com.google.gson.annotations.SerializedName

data class ProductionCompanyTMDB (
        @SerializedName("name") val name: String,
        @SerializedName("id") val id: Int,
        @SerializedName("logo_path") val logo_path: String?,
        @SerializedName("origin_country") val origin_country: String
)