package com.example.moviepedia.model

import com.google.firebase.Timestamp
import java.util.*

data class WatchedItem (
        val id: Long = -1,
        val type: String = "",
        val rating: Int? = null,
        val review: String? = null,
        val addDate: Timestamp? = null,
        val item: FirestoreItem? = null,
        val watchedDate: Date? = null
)