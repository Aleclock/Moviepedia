package com.example.moviepedia.model

import com.google.gson.annotations.SerializedName

data class GenreTMDB (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)