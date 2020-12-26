package com.example.moviepedia.model

import com.google.firebase.Timestamp

data class WatchedItem (
        val id: Long = -1,
        val type: String = "",
        val rating: Int? = null,
        val review: String? = null,
        val addDate: Timestamp? = null,
        val item: FirestoreItem? = null,
        val watchedDate: String? = null
)