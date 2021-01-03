package com.example.moviepedia.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepedia.model.CastMemberTMDB
import kotlinx.android.synthetic.main.list_grid_item.view.*


class CastGridViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {

    fun bindView(cast: CastMemberTMDB) {
        Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w342${cast.profile_path}")
                .fitCenter()
                .into(itemView.imageViewMovie)

        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(colorMatrix)
        itemView.imageViewMovie.colorFilter = filter
    }
}