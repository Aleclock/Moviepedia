package com.example.moviepedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.model.FirestoreList

class ListItemGridAdapter (val context: Context, private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  var listOfList = listOf<FirestoreList>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return DiaryItemGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_grid_item_diary,parent, false))
  }

  override fun getItemCount(): Int {
    return listOfList.size
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    TODO("Not yet implemented")
  }

  fun updateItems(list: MutableList<FirestoreList>) {
    listOfList = list
  }
}