package com.example.moviepedia.tmdb

import com.example.moviepedia.model.CastMemberTMDB
import com.example.moviepedia.model.CrewMemberTMDB
import com.google.gson.annotations.SerializedName

data class GetMovieCreditsResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<CastMemberTMDB>,
    @SerializedName("crew") val crew: List<CrewMemberTMDB>
)
