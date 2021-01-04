package com.example.moviepedia.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.activity.TVShowActivity
import com.example.moviepedia.dialog.TVShowBottomSheet
import com.example.moviepedia.model.TVShowTMDB

class TVShowGridAdapter(val context: Context, private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfTVShows = listOf<TVShowTMDB>()
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
            TVShowBottomSheet().createDialog(context, listOfTVShows[position], layoutInflater)
            true
        }

    }

    fun updateTVShows(tvShows: List<TVShowTMDB>) {
        this.listOfTVShows = tvShows
        notifyDataSetChanged()
    }


}