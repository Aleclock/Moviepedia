package com.example.moviepedia.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import androidx.fragment.app.Fragment
import com.example.moviepedia.activity.TVShowActivity
import com.example.moviepedia.tmdb.TVShow

class TVShowGridAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listOfTVShows = listOf<TVShow>()
    private val TAG = "MovieGridAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TVShowsGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_grid_item,parent, false))
    }

    override fun getItemCount(): Int {
        return listOfTVShows.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TVShowsGridViewHolder).bindView(listOfTVShows[position])

        holder.itemView.setOnClickListener{
            val intent = Intent(context, TVShowActivity::class.java)
            startActivity(context,intent,null)

            Log.d(TAG, listOfTVShows[position].toString())
        }

        holder.itemView.setOnLongClickListener {
            // TODO watched, log or review
            Log.d(TAG, "long click $listOfTVShows[position].toString()")
            true
        }

    }

    fun updateTVShows(tvShows: List<TVShow>) {
        this.listOfTVShows = tvShows
        notifyDataSetChanged()
    }


}