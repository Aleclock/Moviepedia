package com.example.moviepedia.model

import com.google.gson.annotations.SerializedName

data class ProviderTMDB (
    @SerializedName("display_priority") val display_priority: Int,
    @SerializedName("logo_path") val logo_path: String,
    @SerializedName("provider_id") val provider_id: Int,
    @SerializedName("provider_name") val provider_name: String
)