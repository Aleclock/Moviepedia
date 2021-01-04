package com.example.moviepedia.dialog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.example.moviepedia.R
import com.example.moviepedia.activity.MovieActivity
import com.example.moviepedia.activity.TVShowActivity
import com.example.moviepedia.model.TVShowTMDB
import com.google.gson.Gson

open class TVShowBottomSheet {

    fun createDialog(context: Context, tvshow: TVShowTMDB, layoutInflater: LayoutInflater) {
        val mBottomSheetDialog = RoundedBottomSheetDialog(context)
        val sheetView = layoutInflater.inflate(R.layout.tv_show_bottom_sheet, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()

        setMovieInfo(mBottomSheetDialog, tvshow)

        manageButtons(context, mBottomSheetDialog, tvshow)

        //TODO manageToggle(mBottomSheetDialog, movie)
    }

    private fun manageButtons(context: Context, mBottomSheetDialog: RoundedBottomSheetDialog, movie: TVShowTMDB) {

        mBottomSheetDialog.findViewById<TextView>(R.id.tw_tvshow_review_log)!!.setOnClickListener {
            //val intent = Intent(context, SettingsActivity::class.java)
            //intent.putExtra()
            //startActivity(intent)
        }

        mBottomSheetDialog.findViewById<TextView>(R.id.tw_tvshow_add_to_list)!!.setOnClickListener {
            // TODO
        }

        mBottomSheetDialog.findViewById<TextView>(R.id.tw_go_to_tvshow)!!.setOnClickListener {
            val intent = Intent(context, TVShowActivity::class.java)
            intent.putExtra("tvshow", Gson().toJson(movie))
            ContextCompat.startActivity(context, intent, null)
            mBottomSheetDialog.dismiss()
        }
    }

    private fun setMovieInfo(mBottomSheetDialog: RoundedBottomSheetDialog, tvshow: TVShowTMDB) {
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_bs_tv_show_title)?.text =  tvshow.name
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_bs_tv_show_resease_date)?.text = getMovieYear(tvshow.first_air_date)
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_bs_tv_show_rating)?.text = tvshow.rating.toString()
    }

    private fun getMovieYear(date: String): String {
        return if (date.isEmpty())
            ""
        else
            date.substring(0,4)
    }
}