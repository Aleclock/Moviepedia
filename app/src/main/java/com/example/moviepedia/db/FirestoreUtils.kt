package com.example.moviepedia.db

import android.util.Log
import com.example.moviepedia.LoginActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.http.Query

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
        Log.d(TAG, "ciaaao")
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                firestoreCallback.onCallback(document?.data!!)
            } else {
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }
    }

    interface FirestoreCallback {
        fun onCallback (@Query("list") list: MutableMap<String, Any>)
    }
}