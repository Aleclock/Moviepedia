package com.example.moviepedia.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.model.EpisodeTMDB

class EpisodesAdapter (val context: Context, private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  var listOfEpisodes = listOf<EpisodeTMDB>()
  private val TAG = "EpisodesAdapter"

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return EpisodeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.episode_item,parent, false))
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as EpisodeViewHolder).bindView(listOfEpisodes[position])
  }

  override fun getItemCount(): Int {
    return listOfEpisodes.size
  }

  fun updateEpisodes(episodes: List<EpisodeTMDB>) {
    listOfEpisodes = episodes
    notifyDataSetChanged()
  }
}