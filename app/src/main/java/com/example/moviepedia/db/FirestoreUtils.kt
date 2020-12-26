package com.example.moviepedia.db

import android.util.Log
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.model.FirestoreItem
import com.example.moviepedia.model.Movie
import com.example.moviepedia.model.WatchedItem
import com.example.moviepedia.model.WatchlistItem
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
     * ------------------------------------------------------------
     * CREATE VALUE ON FIRESTORE
     * ------------------------------------------------------------
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
     * ------------------------------------------------------------
     * SAVE OR UPDATE VALUE ON FIRESTORE
     * ------------------------------------------------------------
     */

    fun addMovieToWatchlist(userID: FirebaseUser, movie: Movie) {
        val firestoreItem = FirestoreItem().movieToFirestoreItem(movie)
        val watchlistItem = WatchlistItem(movie.id, "movie", Timestamp(Date()),firestoreItem)

        db.collection("movies").document(userID.uid).collection("watchlist").document(movie.id.toString())
                .set(watchlistItem)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun removeMovieToWatchlist(userID: FirebaseUser, movie: Movie) {
        db.collection("movies").document(userID.uid).collection("watchlist").document(movie.id.toString())
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    fun addMovieToWatched(userID: FirebaseUser, movie: Movie) {
        val firestoreItem = FirestoreItem().movieToFirestoreItem(movie)
        val watchedItem = WatchedItem(movie.id,"movie", null, null, Timestamp(Date()),firestoreItem,null)

        db.collection("movies").document(userID.uid).collection("watched").document(movie.id.toString())
                .set(watchedItem)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun removeMovieToWatched(userID: FirebaseUser, movie: Movie) {
        db.collection("movies").document(userID.uid).collection("watched").document(movie.id.toString())
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    fun updateUserStats(key: String) {
        when (key) {
            "watchlist" -> {
                val docRef = db.collection("stats").document(LoginActivity.getUID())
                getWatchlistItems(object : FirestoreWatchlistItemsCallback {
                    override fun onCallback(list: MutableList<WatchlistItem>) {
                        docRef.update(key, list.size)
                    }
                })
            }
            "movies" -> {
                val docRef = db.collection("stats").document(LoginActivity.getUID())
                getWatchedItems(object : FirestoreWatchedItemsCallback {
                    override fun onCallabck(list: MutableList<WatchedItem>) {
                        docRef.update(key, list.size)
                    }
                })
            }
            "tvshow" -> {}
            "rank" -> {}
        }
    }

    /**
     * ------------------------------------------------------------
     * RETRIEVE DATA FROM FIRESTORE
     * ------------------------------------------------------------
     */

    fun getUserData (firestoreCallback: FirestoreCallback) {
        val docRef = db.collection("users").document(LoginActivity.getUID())

        // TODO non bellissimo, funziona ma devo aggiungere cose nel callback
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
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

    fun getWatchlistItems(firestoreWatchlistItemsCallback: FirestoreWatchlistItemsCallback) {
        val docRef = db.collection("movies").document(LoginActivity.getUID()).collection("watchlist")
        docRef.get()
                .addOnSuccessListener { documents ->
                    val itemList =  mutableListOf<WatchlistItem>()
                    for (doc in documents) {
                        itemList.add(doc.toObject(WatchlistItem::class.java))
                    }
                    firestoreWatchlistItemsCallback.onCallback(itemList)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
    }

    fun getWatchedItems(firestoreWatchedItemsCallback: FirestoreWatchedItemsCallback) {
        val docRef = db.collection("movies").document(LoginActivity.getUID()).collection("watched")
        docRef.get()
            .addOnSuccessListener { documents ->
                val itemList =  mutableListOf<WatchedItem>()
                for (doc in documents) {
                    itemList.add(doc.toObject(WatchedItem::class.java))
                }
                firestoreWatchedItemsCallback.onCallabck(itemList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun isInWatchlist(userID: FirebaseUser, movie: Movie, firestorePresenceCallback: FirestorePresenceCallback){
        val item: MutableMap<String, Any?> = HashMap()

        db.collection("movies").document(userID.uid).collection("watchlist").document(movie.id.toString())
            .get()
            .addOnSuccessListener { document ->
                val watchedItem = document.toObject(WatchlistItem::class.java)
                if (document.data != null) {
                    item["presence"] = true
                    item["item"] = watchedItem
                } else {
                    item["presence"] = false
                    item["item"] = null
                }
                firestorePresenceCallback.onCallback(item)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                item["presence"] = false
                item["item"] = null
                firestorePresenceCallback.onCallback(item)
            }
    }

    fun isInWatched(userID: FirebaseUser, movie: Movie, firestorePresenceCallback: FirestorePresenceCallback){
        val item: MutableMap<String, Any?> = HashMap()

        db.collection("movies").document(userID.uid).collection("watched").document(movie.id.toString())
            .get()
            .addOnSuccessListener { document ->
                val watchedItem = document.toObject(WatchedItem::class.java)
                if (document.data != null) {
                    item["presence"] = true
                    item["item"] = watchedItem
                } else {
                    item["presence"] = false
                    item["item"] = null
                }
                firestorePresenceCallback.onCallback(item)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                item["presence"] = false
                item["item"] = null
                firestorePresenceCallback.onCallback(item)
            }
    }


    /**
     * INTERFACE FOR CALLBACKS
     */

    interface FirestoreCallback {
        fun onCallback (@Query("list") list: MutableMap<String, Any?>)
    }

    interface FirestorePresenceCallback {
        fun onCallback (@Query("value") value: MutableMap<String, Any?>)
    }

    interface FirestoreWatchlistItemsCallback {
        fun onCallback (@Query("list") list: MutableList<WatchlistItem>)
    }

    interface FirestoreWatchedItemsCallback {
        fun onCallabck (@Query("list") list: MutableList<WatchedItem>)
    }

    interface FirestoreItemsCallback {
        fun onCallback (@Query("list") list: MutableList<DocumentSnapshot>)
    }
}
