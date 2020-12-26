package com.example.moviepedia.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepedia.model.FirestoreItem
import kotlinx.android.synthetic.main.list_grid_item.view.*

class FirestoreItemGridViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {

    val TAG = "FirestoreItemGridViewHo"

    fun bindView(firestoreItem: FirestoreItem) {

        Log.d(TAG, itemView.imageViewMovie.width.toString() + " , " + itemView.imageViewMovie.width)
        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/w342${firestoreItem.posterPath}")
            .into(itemView.imageViewMovie)
    }
}