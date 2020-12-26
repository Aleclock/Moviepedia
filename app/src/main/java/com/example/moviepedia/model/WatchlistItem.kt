package com.example.moviepedia.model

import com.google.firebase.Timestamp

data class WatchlistItem(
        val id: Long = -1,
        val type: String = "",
        val addDate: Timestamp? = null,
        val item: FirestoreItem? = null
)

