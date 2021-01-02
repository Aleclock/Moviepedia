package com.example.moviepedia.tmdb

import com.google.gson.annotations.SerializedName

// TODO valutare se farlo o no, è un lavorone forse inutile
data class GetMovieProvidersResponse (
    @SerializedName("id") val id: Int
)