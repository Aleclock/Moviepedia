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
import com.example.moviepedia.dialog.MovieBottomSheet
import com.example.moviepedia.tmdb.Movie

class MovieGridAdapter(val context: Context, val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            val intent = Intent(context, MovieActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
            Log.d(TAG, listOfMovies[position].toString())
        }

        holder.itemView.setOnLongClickListener {

            MovieBottomSheet().createDialog(context, listOfMovies[position], layoutInflater)

            Log.d(TAG, "long click $listOfMovies[position].toString()")
            true
        }
    }

    fun updateMovies(movies: List<Movie>) {
        this.listOfMovies = movies
        notifyDataSetChanged()
    }


}
