package com.example.moviepedia.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.model.TVShowTMDB
import kotlinx.android.synthetic.main.list_grid_item.view.*

class ItemGridViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
  fun bindView(item: Any) {
    if (item::class == MovieTMDB::class) {
      val movie = item as MovieTMDB
      Glide.with(itemView.context)
          .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
          .fitCenter()
          .into(itemView.imageViewMovie)
    } else if (item::class == TVShowTMDB::class) {
      val tvshow = item as TVShowTMDB
      Glide.with(itemView.context)
          .load("https://image.tmdb.org/t/p/w342${tvshow.posterPath}")
          .fitCenter()
          .into(itemView.imageViewMovie)
    }
  }
}