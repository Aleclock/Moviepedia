package com.example.moviepedia.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviepedia.model.WatchedItem
import kotlinx.android.synthetic.main.list_grid_item_diary.view.*
import java.util.*

class DiaryItemGridViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
    val TAG = "ItemGridViewHolder"

    fun bindView(item: WatchedItem) {
        if (item.type == "movie") {
            val itemDetail = item.item!!

            itemView.tw_diary_title.text = itemDetail.title
            itemView.tw_diary_date.text = getDayDate(item.watchedDate!!)
            // TODO show rating
            // TODO show if exist review (as symbol)
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