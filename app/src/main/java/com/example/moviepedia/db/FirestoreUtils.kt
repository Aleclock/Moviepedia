package com.example.moviepedia.db

import android.util.Log
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.tmdb.Movie
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.http.Query
import java.util.*
import kotlin.collections.HashMap

open class FirestoreUtils {

    private val TAG = "FirestoreUtils"
    private val db = FirebaseFirestore.getInstance()

    /**
     * CREATE VALUE ON FIRESTORE
     */

    fun saveUserToDB(userID: FirebaseUser, email: String, username: String) {
        val user: MutableMap<String, Any> = HashMap()
        user["userID"] = userID.uid
        user["email"] = email
        user["username"] = username

        db.collection("users").document(userID.uid)
                .set(user)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun createUserStats(userID: FirebaseUser) {
        val userStats: MutableMap<String, Any> = HashMap()
        userStats["rank"] = 1
        userStats["movies"] = 0
        userStats["tvshow"] = 0
        userStats["watchlist"] = 0

        db.collection("stats").document(userID.uid)
            .set(userStats)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    /**
     * SAVE OR UPDATE VALUE ON FIRESTORE
     */

    fun addMovieToWatchlist(userID: FirebaseUser, movie: Movie) {
        val watchlistItem: MutableMap<String, Any> = HashMap()
        watchlistItem["movieID"] = movie.id
        watchlistItem["addDate"] = Timestamp(Date())
        watchlistItem["movie"] = movie
        watchlistItem["type"] = "movie"

        db.collection("movies").document(userID.uid).collection("watchlist").document(movie.id.toString())
                .set(watchlistItem)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun addMovieToWatched(userID: FirebaseUser, movie: Movie) {
        val watchedItem: MutableMap<String, Any?> = HashMap()
        watchedItem["movieID"] = movie.id
        watchedItem["addDate"] = Timestamp(Date())
        watchedItem["rating"] = null
        watchedItem["review"] = null
        watchedItem["watchedDate"] = null
        watchedItem["movie"] = movie
        watchedItem["type"] = "movie"

        db.collection("movies").document(userID.uid).collection("watched").document(movie.id.toString())
                .set(watchedItem)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun updateUserStats(key: String) {
        when (key) {
            "watchlist" -> {
                val docRef = db.collection("stats").document(LoginActivity.getUID())
                getWatchlistItems(object : FirestoreItemsCallback {
                    override fun onCallback(list: MutableList<DocumentSnapshot>) {
                        docRef.update(key, list.size)
                    }
                })
            }
            "movies" -> {
                val docRef = db.collection("stats").document(LoginActivity.getUID())
                getWatchedItems(object : FirestoreItemsCallback {
                    override fun onCallback(list: MutableList<DocumentSnapshot>) {
                        docRef.update(key, list.size)
                    }
                })
            }
            "tvshow" -> {}
            "rank" -> {}
        }
    }

    /**
     * RETRIEVE DATA FROM FIRESTORE
     */

    fun getUserData (firestoreCallback: FirestoreCallback) {
        val docRef = db.collection("users").document(LoginActivity.getUID())

        // TODO non bellissimo, funziona ma devo aggiungere cose nel callback
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                Log.d(TAG, "Cached document data: ${document?.data}")
                firestoreCallback.onCallback(document?.data!!)
            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }
    }

    fun getUserStats(firestoreCallback: FirestoreCallback) {
        val docRef = db.collection("stats").document(LoginActivity.getUID())
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                firestoreCallback.onCallback(document?.data!!)
            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }
    }

    fun getWatchlistItems(firestoreItemsCallback: FirestoreItemsCallback) {
        val docRef = db.collection("movies").document(LoginActivity.getUID()).collection("watchlist")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                firestoreItemsCallback.onCallback(document?.documents!!)
            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }
    }

    fun getWatchedItems(firestoreItemsCallback: FirestoreItemsCallback) {
        val docRef = db.collection("movies").document(LoginActivity.getUID()).collection("watched")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                firestoreItemsCallback.onCallback(document?.documents!!)
            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }
    }

    interface FirestoreCallback {
        fun onCallback (@Query("list") list: MutableMap<String, Any>)
    }

    interface FirestoreItemsCallback {
        fun onCallback (@Query("list") list: MutableList<DocumentSnapshot>)
    }
}