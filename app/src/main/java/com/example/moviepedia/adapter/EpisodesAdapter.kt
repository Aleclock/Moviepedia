package com.example.moviepedia.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.model.EpisodeTMDB
import com.example.moviepedia.model.FirestoreEpisode

class EpisodesAdapter (val context: Context, private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  var listOfEpisodes = listOf<EpisodeTMDB>()
  var listOfWatched = listOf<FirestoreEpisode>()
  private val TAG = "EpisodesAdapter"

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return EpisodeViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.episode_item, parent, false)
    )
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as EpisodeViewHolder).bindView(listOfEpisodes[position])
    if (position < listOfWatched.size) {
      if (listOfEpisodes[position].id == listOfWatched[position]!!.id_episode) {
        holder.SetAsWatched(context)
      }
    }
  }

  override fun getItemCount(): Int {
    return listOfEpisodes.size
  }

  fun updateEpisodes(episodes: List<EpisodeTMDB>) {
    listOfEpisodes = episodes
    notifyDataSetChanged()
  }

  fun watchedEpisodes(list: MutableList<FirestoreEpisode>) {
    listOfWatched = list
    notifyDataSetChanged()
  }

  fun addEpisode(pos: Int, episode: FirestoreEpisode) {
    listOfWatched.toMutableList().add(pos, episode)
    notifyDataSetChanged()
  }
}