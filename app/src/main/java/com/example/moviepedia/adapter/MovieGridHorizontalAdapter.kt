package com.example.moviepedia.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.activity.MovieActivity
import com.example.moviepedia.dialog.MovieBottomSheet
import com.example.moviepedia.model.MovieTMDB
import com.google.gson.Gson

class MovieGridHorizontalAdapter(val context: Context, private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfMovies = listOf<MovieTMDB>()
    private val TAG = "MovieGridAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_horizontal_grid_item,parent, false))
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieGridViewHolder).bindView(listOfMovies[position])

        holder.itemView.setOnClickListener{
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra("movie", Gson().toJson(listOfMovies[position]))
            ContextCompat.startActivity(context, intent, null)
        }

        holder.itemView.setOnLongClickListener {
            MovieBottomSheet().createDialog(context, listOfMovies[position], layoutInflater)
            true
        }
    }

    fun updateMovies(movies: List<MovieTMDB>) {
        this.listOfMovies = movies
        notifyDataSetChanged()
    }


}
