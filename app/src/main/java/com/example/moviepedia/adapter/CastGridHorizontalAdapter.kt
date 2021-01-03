package com.example.moviepedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.R
import com.example.moviepedia.model.CastMemberTMDB

class CastGridHorizontalAdapter(
        val context: Context,
        private val layoutInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfCast = listOf<CastMemberTMDB>()
    private val TAG = "MovieGridAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CastGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_horizontal_grid_item,parent, false))
    }

    override fun getItemCount(): Int {
        return listOfCast.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CastGridViewHolder).bindView(listOfCast[position])

        /*holder.itemView.setOnClickListener{
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra("movie", Gson().toJson(listOfCast[position]))
            ContextCompat.startActivity(context, intent, null)
        }

        holder.itemView.setOnLongClickListener {
            //MovieBottomSheet().createDialog(context, listOfCast[position], layoutInflater)
            //TODO fare bottom sheet con attori
            true
        }*/
    }

    fun updateCast(cast: List<CastMemberTMDB>) {
        this.listOfCast = cast
        notifyDataSetChanged()
    }


}
