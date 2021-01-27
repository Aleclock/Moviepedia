package com.example.moviepedia.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.activity.TVShowActivity
import com.example.moviepedia.dialog.TVShowBottomSheet
import com.example.moviepedia.model.TVShowTMDB
import com.google.gson.Gson

class TVShowGridHorizontalAdapter(val context: Context, private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var listOfTVShow = listOf<TVShowTMDB>()
  private val TAG = "TVShowGridHorizontalAdapter"

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return ItemGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_horizontal_grid_item,parent, false))
  }

  override fun getItemCount(): Int {
    return listOfTVShow.size
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as ItemGridViewHolder).bindView(listOfTVShow[position])

    holder.itemView.setOnClickListener{
      val intent = Intent(context, TVShowActivity::class.java)
      intent.putExtra("tvshow", Gson().toJson(listOfTVShow[position]))
      ContextCompat.startActivity(context, intent, null)
    }

    holder.itemView.setOnLongClickListener {
      TVShowBottomSheet().createDialog(context, listOfTVShow[position], layoutInflater)
      true
    }
  }

  fun updateMovies(tvShow: List<TVShowTMDB>) {
    this.listOfTVShow = tvShow
    notifyDataSetChanged()
  }
}