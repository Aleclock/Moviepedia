package com.example.moviepedia.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.R
import com.example.moviepedia.db.FirestoreUtils
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.model.TVShowTMDB
import com.example.moviepedia.utils.DateDialog
import com.example.moviepedia.utils.SliderRatingBar
import com.jaygoo.widget.RangeSeekBar
import kotlinx.android.synthetic.main.movie_review_bottom_sheet.*

// TODO quando viene inviato il review, è necessario chiamare la funzione che permette di aggiornare le statistiche
open class ItemLogBottomSheet {
    val TAG = "ItemLogBottomSheet"

    fun createDialog(context: Context, item: Any, layoutInflater: LayoutInflater) {
        // TODO do the same thing with tv show or bo non so ancora come fare

        if (item.javaClass == TVShowTMDB::class.java) {
            // TODO inflate tv show layout
        } else if (item.javaClass == MovieTMDB::class.java) {
            val bottomSheet = inflateLayout(context, layoutInflater, R.layout.movie_review_bottom_sheet)
            setMovieInfo(bottomSheet, item as MovieTMDB)
            val dateDialog  = setDatePicker(context, bottomSheet)
            val seekbar = initRatingBar(bottomSheet)
            initReviewinputs(bottomSheet, seekbar, item, dateDialog)
        }
    }

    private fun initRatingBar(bottomSheet: RoundedBottomSheetDialog): SliderRatingBar {
        val seekbar = bottomSheet.findViewById<RangeSeekBar>(R.id.range_seekbar_review)
        val sliderRatingBar = SliderRatingBar()
        if (seekbar != null) {
            sliderRatingBar.setSeekBarVisible(seekbar)
            sliderRatingBar.initRatingBar(seekbar)
        }
        return sliderRatingBar
    }

    private fun inflateLayout(context: Context, layoutInflater: LayoutInflater, layout  :Int): RoundedBottomSheetDialog {
        val mBottomSheetDialog = RoundedBottomSheetDialog(context)
        val sheetView = layoutInflater.inflate(layout, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()

        return mBottomSheetDialog
    }

    private fun initReviewinputs(btmDialog: RoundedBottomSheetDialog, seekbar: SliderRatingBar, item: Any, dateDialog: DateDialog) {
        btmDialog.btn_review.setOnClickListener {
            val textReview = btmDialog.et_item_review.text.toString()
            SliderRatingBar().getProgress()
            val rating = seekbar.getProgress()
            val date = dateDialog.date
            Log.d(TAG, date.toString())
            if (item.javaClass == TVShowTMDB::class.java) {
                // TODO log tv show review
            } else {
                FirestoreUtils().addMovieToWatched(LoginActivity.getUser(), item as MovieTMDB, "movie", rating?.toInt(), textReview, date?.time)
                btmDialog.dismiss()
            }
        }
    }

    private fun setDatePicker(context : Context, btmDialog: RoundedBottomSheetDialog): DateDialog {
        val dateDialog = DateDialog()
        dateDialog.initDatePicker(context, btmDialog)
        return dateDialog
    }

    private fun setMovieInfo(btmDialog: RoundedBottomSheetDialog, movie: MovieTMDB) {
        btmDialog.findViewById<TextView>(R.id.tw_bs_movie_log_title)?.text =  movie.title
        btmDialog.findViewById<TextView>(R.id.tw_bs_movie_log_resease_date)?.text = getMovieYear(movie.releaseDate)
    }

    private fun getMovieYear(date: String): String {
        return if (date.isEmpty())
            ""
        else
            date.substring(0,4)
    }
}