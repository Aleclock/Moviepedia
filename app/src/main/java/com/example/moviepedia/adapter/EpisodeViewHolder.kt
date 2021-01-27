package com.example.moviepedia.adapter
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.model.EpisodeTMDB
import kotlinx.android.synthetic.main.episode_item.view.*
import java.util.*

class EpisodeViewHolder  (itemView: View): RecyclerView.ViewHolder (itemView) {
  val TAG = "EpisodeViewHolder"

  fun bindView(item: EpisodeTMDB) {
    itemView.tw_episode_title.text = item.name
    itemView.tw_episode_number.text = item.episode_number.toString()
    itemView.tw_episode_date.text = item.air_date
  }

  private fun getDayDate(date: Date): String {
    val cal = Calendar.getInstance(Locale.ENGLISH)
    cal.time = date;
    return cal.get(Calendar.DAY_OF_MONTH).toString()
  }
}
