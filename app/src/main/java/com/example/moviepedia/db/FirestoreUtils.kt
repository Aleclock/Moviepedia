package com.example.moviepedia.db

import android.util.Log
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.model.*
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.http.Query
import java.util.*
import kotlin.collections.HashMap

/**
 * List of methods:
 *  - saveUserToDB : create user ref in DB
 *  - createUserStats : craete user stats reg in DB
 *  - addMovieToWatchlist given a movie, add it to watchlist collection in db
 *  - removeMovieToWatchlist: given a movie, remove it from watchlist collection in db
 *  - addMovieToWatched: given a movie, add it to watched collection in db
 *  - removeMovieFromWatched: given a movie, remove it from watched collection in db
 *  - addTVShowToWatched: given a tvshow, add it to watched collection in db
 *  - removeTVShowFromWatched: given a tvshow, remove it from watched collection in db TODO
 *  - updateWatchedItem: given a movie, update its value (rating, review)
 *  - updateUserStats: given a key (watchlist, watched, ...), update stats (number of element in db
 *  - getUserData: given a user, return its data
 *  - getUserStats: given a user, return its stats (number of elements in wathlist, wat ched)
 *  - getWatchlistItems: return every element in watchlist collection in db
 *  - getWatchedItems: return every element in watched collection in db
 *  - getDiaryItems: return every element in diary collection in db
 *  - isMovieInWatchlist: given a user and a movie, verify if item is in watchlist collection in db
 *  - isMovieInWatched: given a user and a movie, verify if item is in watched collection in db
 *  - isTVShowInWatched: given a user and a tvshow, verify if item is in watched collection in db
 *  - removeTVShowEpisodeFromWatched: given user and episode, remove episode from db collection
 *  - getTVShowWatched: given user and tvshow, return all episode of tvshow already watched
 *  - TVShowSeasonWatched: given user, tvshow and season, return all tvshow episode watched of a specific season
 *  - getTVShowEpisodeWatched: given user and specific episode, return specific episode from db
 *  - createEmptyList: given user and list name (string), create an empty list
 *  - getUserList: given user, return all list created by user
 */

open class FirestoreUtils {

  private val TAG = "FirestoreUtils"
  private val db = FirebaseFirestore.getInstance()

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

  fun addMovieToWatchlist(userID: FirebaseUser, movie: MovieTMDB) {
    val firestoreItem = FirestoreItem().movieToFirestoreItem(movie)
    val watchlistItem = WatchlistItem(movie.id, "movie", Timestamp(Date()),firestoreItem)

    isMovieInWatchlist(LoginActivity.getUser(), movie, object : FirestorePresenceCallback {
      override fun onCallback(value: MutableMap<String, Any?>) {
        if (!(value["presence"] as Boolean)) {
          db.collection("movies").document(userID.uid).collection("watchlist").document(movie.id.toString())
            .set(watchlistItem)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
      }
    })
  }

  fun removeMovieToWatchlist(userID: FirebaseUser, movie: MovieTMDB) {
    db.collection("movies").document(userID.uid).collection("watchlist").document(movie.id.toString())
      .delete()
      .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
      .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
  }

  fun addMovieToWatched(userID: FirebaseUser, movie: MovieTMDB, rating: Int?, review: String?, watchedDate: Date?) {
    val firestoreItem = FirestoreItem().movieToFirestoreItem(movie)
    val watchedItem = WatchedItem(movie.id,"movie", rating, review, Timestamp(Date()),firestoreItem, watchedDate)

    isMovieInWatched(LoginActivity.getUser(), movie, object : FirestorePresenceCallback {
      override fun onCallback(value: MutableMap<String, Any?>) {
        if (!(value["presence"] as Boolean)) {
          db.collection("movies").document(userID.uid).collection("watched").document(movie.id.toString())
            .set(watchedItem)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        } else
          Log.e(TAG, "Error adding movie" + movie.title + "to watched")
      }
    })
  }

  fun addTVShowEpisodeToWatched (userID: FirebaseUser, tvShow: TVShowTMDB, seasonsTMDB: SeasonsTMDB, episode: EpisodeTMDB) {
    val firestoreEpisode = FirestoreEpisode().episodeToFirestoreEpisode(tvShow, seasonsTMDB, episode)
    firestoreEpisode.addDate = Timestamp(Date())

    //db.collection("movies").document(userID.uid).collection("tvshow_episodes").document(tvShow.id.toString())
    db.collection("movies").document(userID.uid).collection("tvshow_episodes").document(episode.id.toString())
      .set(firestoreEpisode)
      .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
      .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
  }

  fun getTVShowWatched (userID: FirebaseUser, tvShow: TVShowTMDB, firestoreWatchedEpisodeCallback: FirestoreWatchedEpisodeCallback
  ) {
    val itemList =  mutableListOf<FirestoreEpisode>()
    val docRef = db.collection("movies").document(userID.uid).collection("tvshow_episodes")
      .whereEqualTo("id_tvshow", tvShow.id)
    docRef.get()
      .addOnSuccessListener { documents ->
        for (doc in documents) {
          itemList.add(doc.toObject(FirestoreEpisode::class.java))
        }
        firestoreWatchedEpisodeCallback.onCallback(itemList)
      }
      .addOnFailureListener {exception ->
        Log.w(TAG, "Error getting documents: ", exception)
      }
  }

  fun TVShowSeasonWatched (userID: FirebaseUser, tvShow: TVShowTMDB, seasonsTMDB: SeasonsTMDB, firestoreWatchedEpisodeCallback: FirestoreWatchedEpisodeCallback) {
    val itemList =  mutableListOf<FirestoreEpisode>()
    val docRef = db.collection("movies").document(userID.uid).collection("tvshow_episodes")
      .whereEqualTo("id_tvshow", tvShow.id)
      .whereEqualTo("id_season", seasonsTMDB.id)
    docRef.get()
      .addOnSuccessListener { documents ->
        for (doc in documents) {
          itemList.add(doc.toObject(FirestoreEpisode::class.java))
        }
        firestoreWatchedEpisodeCallback.onCallback(itemList)
      }
      .addOnFailureListener {exception ->
        Log.w(TAG, "Error getting documents: ", exception)
      }
  }

  fun getTVShowEpisodeWatched (
    userID: FirebaseUser,
    episode: EpisodeTMDB,
    firestoreWatchedEpisodeCallback: FirestoreWatchedEpisodeCallback
  ) {
    val itemList =  mutableListOf<FirestoreEpisode>()
    db.collection("movies").document(userID.uid)
      .collection("tvshow_episodes").document(
      episode.id.toString())
      .get()
      .addOnSuccessListener { document ->
        itemList.add(document.toObject(FirestoreEpisode::class.java)!!)
        firestoreWatchedEpisodeCallback.onCallback(itemList)
      }
      .addOnFailureListener {exception ->
        Log.w(TAG, "Error getting documents: ", exception)
      }
  }

  /**
   * Add TVShow to watched (call it if every episode are watched
   */
  fun addTVShowToWatched(userID: FirebaseUser, tvShow: TVShowTMDB, rating: Int?, review: String?, watchedDate: Date?) {
    val firestoreItem = FirestoreItem().tvShowToFirestoreItem(tvShow)
    val watchedItem = WatchedItem(tvShow.id,"movie", rating, review, Timestamp(Date()),firestoreItem, watchedDate)
    isTVShowInWatched(LoginActivity.getUser(), tvShow, object : FirestorePresenceCallback {
      override fun onCallback(value: MutableMap<String, Any?>) {
        if (!(value["presence"] as Boolean)) {
          db.collection("movies").document(userID.uid).collection("watched").document(tvShow.id.toString())
            .set(watchedItem)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        } else
          Log.e(TAG, "Error adding tvshow" + tvShow.name + "to watched")
      }
    })
  }

  fun removeMovieFromWatched(userID: FirebaseUser, movie: MovieTMDB) {
    db.collection("movies").document(userID.uid).collection("watched").document(movie.id.toString())
      .delete()
      .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
      .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
  }

  fun removeTVShowEpisodeFromWatched (userID: FirebaseUser,episode: EpisodeTMDB) {
    db.collection("movies").document(userID.uid)
      .collection("tvshow_episodes").document(episode.id.toString())
      .delete()
      .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
      .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
  }

  fun updateWatchedItem(userID: FirebaseUser, movie_id: Long, key: String, value: Any) {
    db.collection("movies").document(userID.uid).collection("watched").document(movie_id.toString())
    . update(key, value)
  }

  // TODO potrei chiamare questa funzione direttamente quando aggiungo gli elementi al database, così non devo invocarle nei fragment o cose
  fun updateUserStats(key: String) {
    val docRef = db.collection("stats").document(LoginActivity.getUID())
    when (key) {
      "watchlist" -> {
          getWatchlistItems(object : FirestoreWatchlistItemsCallback {
              override fun onCallback(list: MutableList<WatchlistItem>) {
                  docRef.update(key, list.size)
              }
          })
      }
      "movie" -> {
          getWatchedItems(object : FirestoreWatchedItemsCallback {
              override fun onCallback(list: MutableList<WatchedItem>) {
                  docRef.update(key, list.size)
              }
          })
      }
      "tvshow" -> {
          // TODO create getWatchedTVShow
      }
      "rank" -> {}
    }
  }

  // TODO non bellissimo, funziona ma devo aggiungere cose nel callback
  fun getUserData (firestoreCallback: FirestoreCallback) {
    val docRef = db.collection("users").document(LoginActivity.getUID())
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
    val docRef = db.collection("movies")
      .document(LoginActivity.getUID())
      .collection("watchlist")
      .orderBy("addDate", com.google.firebase.firestore.Query.Direction.DESCENDING)
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
    val docRef = db.collection("movies")
      .document(LoginActivity.getUID())
      .collection("watched")
      .orderBy("addDate", com.google.firebase.firestore.Query.Direction.DESCENDING)
    docRef.get()
      .addOnSuccessListener { documents ->
        val itemList =  mutableListOf<WatchedItem>()
        for (doc in documents) {
          itemList.add(doc.toObject(WatchedItem::class.java))
        }
        firestoreWatchedItemsCallback.onCallback(itemList)
      }
      .addOnFailureListener { exception ->
        Log.w(TAG, "Error getting documents: ", exception)
      }
  }

  fun getDiaryItems(firestoreWatchedItemsCallback: FirestoreWatchedItemsCallback) {
    val docRef = db.collection("movies").document(LoginActivity.getUID())
      .collection("watched")
      .whereNotEqualTo("watchedDate", null)
      .orderBy("watchedDate", com.google.firebase.firestore.Query.Direction.DESCENDING)
    docRef.get()
      .addOnSuccessListener { documents ->
        val itemList =  mutableListOf<WatchedItem>()
        for (doc in documents) {
          itemList.add(doc.toObject(WatchedItem::class.java))
        }
        firestoreWatchedItemsCallback.onCallback(itemList)
      }
      .addOnFailureListener {exception ->
        Log.w(TAG, "Error getting documents: ", exception)
      }
  }

  fun isMovieInWatchlist(userID: FirebaseUser, movie: MovieTMDB, firestorePresenceCallback: FirestorePresenceCallback){
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

  fun isTVShowInWatched(userId: FirebaseUser, tvShow: TVShowTMDB, firestorePresenceCallback: FirestorePresenceCallback){
    val item: MutableMap<String, Any?> = HashMap()
    db.collection("movies").document(userId.uid).collection("watched").document(tvShow.id.toString())
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

  fun isMovieInWatched(userID: FirebaseUser, movie: MovieTMDB, firestorePresenceCallback: FirestorePresenceCallback){
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

  fun createEmptyList (userID: FirebaseUser, name: String) {
    val list = FirestoreList().createList(userID.uid, name)
    db.collection("list")
      .add(list)
      .addOnSuccessListener { documentReference -> Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}") }
      .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
  }

  fun getUserList(userID: FirebaseUser, firestoreListCallback: FirestoreListCallback) {
    val docRef = db.collection("list")
      .whereEqualTo("user_id", userID.uid)
    docRef.get()
      .addOnSuccessListener { documents ->
        val itemList =  mutableListOf<FirestoreList>()
        for (doc in documents) {
          itemList.add(doc.toObject(FirestoreList::class.java))
        }
        firestoreListCallback.onCallback(itemList)
      }
      .addOnFailureListener {exception ->
        Log.w(TAG, "Error getting documents: ", exception)
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

  interface FirestoreWatchedEpisodeCallback {
    fun onCallback (@Query("list") list: MutableList<FirestoreEpisode>)
  }

  interface FirestoreWatchedItemsCallback {
    fun onCallback (@Query("list") list: MutableList<WatchedItem>)
  }

  interface FirestoreListCallback {
    fun onCallback (@Query("list") list: MutableList<FirestoreList>)
  }

  interface FirestoreItemsCallback {
    fun onCallback (@Query("list") list: MutableList<DocumentSnapshot>)
  }
}
