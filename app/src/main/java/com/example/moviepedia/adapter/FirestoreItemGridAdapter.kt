package com.example.moviepedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.model.FirestoreItem

class FirestoreItemGridAdapter (val context: Context, private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfItem = listOf<FirestoreItem>()
    private val TAG = "FirestoreItemGridAd"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FirestoreItemGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_grid_item,parent, false))
    }

    override fun getItemCount(): Int {
        return listOfItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FirestoreItemGridViewHolder).bindView(listOfItem[position])
    }

    fun updateItems(items: List<FirestoreItem>) {
        this.listOfItem = items
        notifyDataSetChanged()
    }
}