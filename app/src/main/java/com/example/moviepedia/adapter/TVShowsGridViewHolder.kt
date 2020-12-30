package com.example.moviepedia.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepedia.model.TVShowTMDB
import kotlinx.android.synthetic.main.list_grid_item.view.*

class TVShowsGridViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {

    fun bindView(show: TVShowTMDB) {
        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/w342${show.posterPath}")
            .fitCenter()
            .into(itemView.imageViewMovie)
    }
}