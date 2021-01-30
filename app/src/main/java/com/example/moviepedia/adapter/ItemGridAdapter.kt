package com.example.moviepedia.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.activity.MovieActivity
import com.example.moviepedia.activity.TVShowActivity
import com.example.moviepedia.dialog.MovieBottomSheet
import com.example.moviepedia.dialog.TVShowBottomSheet
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.model.TVShowTMDB
import com.google.gson.Gson

class ItemGridAdapter (val context: Context, private val layoutInflater: LayoutInflater): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var listOfItems = listOf<Any>()
  private val TAG = "MovieGridAdapter"

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return ItemGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_grid_item,parent, false))
  }

  override fun getItemCount(): Int {
    return listOfItems.size
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as ItemGridViewHolder).bindView(listOfItems[position])

    holder.itemView.setOnClickListener{
      if (listOfItems[position]::class == MovieTMDB::class) {
        val intent = Intent(context, MovieActivity::class.java)
        intent.putExtra("movie", Gson().toJson(listOfItems[position] as MovieTMDB))
        ContextCompat.startActivity(context, intent, null)
      } else if (listOfItems[position]::class == TVShowTMDB::class) {
        val intent = Intent(context, TVShowActivity::class.java)
        intent.putExtra("tvshow", Gson().toJson(listOfItems[position] as TVShowTMDB))
        ContextCompat.startActivity(context, intent, null)
      }
    }

    holder.itemView.setOnLongClickListener {
      if (listOfItems[position]::class == MovieTMDB::class)
        MovieBottomSheet().createDialog(context, listOfItems[position] as MovieTMDB, layoutInflater)
      else if (listOfItems[position]::class == TVShowTMDB::class)
        TVShowBottomSheet().createDialog(context,listOfItems[position] as TVShowTMDB, layoutInflater)
      true
    }
  }

  fun updateItems(items: List<Any>) {
    this.listOfItems = items
    notifyDataSetChanged()
  }
}