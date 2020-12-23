package com.example.moviepedia.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.tmdb.Movie

class MovieGridAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listOfMovies = listOf<Movie>()
    private val TAG = "MovieGridAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_grid_item,parent, false))
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieGridViewHolder).bindView(listOfMovies[position])

        holder.itemView.setOnClickListener{
            // TODO open new page
            Log.d(TAG, listOfMovies[position].toString())
        }
    }

    fun updateMovies(movies: List<Movie>) {
        this.listOfMovies = movies
        notifyDataSetChanged()
    }


}