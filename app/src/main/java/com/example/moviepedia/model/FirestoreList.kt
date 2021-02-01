package com.example.moviepedia.model

import com.google.firebase.Timestamp
import java.util.*

class FirestoreList (
  var user_id : String = "",
  var list_name: String = "",
  var creation_date: Timestamp? = null,
  var last_update_date: Timestamp? = null,
  var n_follower: Int? = null,
  var n_like : Int? = null,
  val items: List<FirestoreItem>? = listOf()
) {
  fun createList(userID: String, name: String): FirestoreList {
    return FirestoreList(
      userID,
      name,
      Timestamp(Date()),
      Timestamp(Date()),
      0,
      0,
      listOf())
  }
}