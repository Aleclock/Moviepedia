package com.example.moviepedia.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepedia.tmdb.Movie
import kotlinx.android.synthetic.main.list_grid_item.view.*

class MovieGridViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {

    fun bindView(movie: Movie) {
        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
            .fitCenter()
            .into(itemView.imageViewMovie)
    }
}