package com.example.moviepedia.adapter

import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepedia.R
import com.example.moviepedia.model.WatchedItem
import kotlinx.android.synthetic.main.list_grid_item_diary.view.*
import java.util.*

class DiaryItemGridViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
    val TAG = "DiaryItemGridViewHolder"

    fun bindView(item: WatchedItem) {
        if (item.type == "movie") {
            val itemDetail = item.item!!

            itemView.tw_diary_title.text = itemDetail.title
            itemView.tw_diary_date.text = getDayDate(item.watchedDate!!)
            itemView.tw_diary_rating.text = item.rating.toString()

            if (item.review != "")
                itemView.btn_diary_review.visibility = View.VISIBLE

            Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w342${itemDetail.posterPath}")
                    .fitCenter()
                    .into(itemView.imageViewItem_diary)
        } else if (item.type == "tvshow") {
            /*val itemDetail = item.item as TVShowTMDB

            Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w342${itemDetail.posterPath}")
                    .fitCenter()
                    .into(itemView.imageViewMovie)*/
        }
    }

    private fun getDayDate(date: Date): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.time = date;
        return cal.get(Calendar.DAY_OF_MONTH).toString()
    }
}

