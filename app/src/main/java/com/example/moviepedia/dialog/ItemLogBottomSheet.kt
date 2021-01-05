package com.example.moviepedia.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.example.moviepedia.R
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.model.TVShowTMDB
import kotlinx.android.synthetic.main.movie_review_bottom_sheet.*
import java.text.SimpleDateFormat
import java.util.*

open class ItemLogBottomSheet {
    val TAG = "ItemLogBottomSheet"

    fun createDialog(context: Context, item: Any, layoutInflater: LayoutInflater) {
        // TODO Inflate correct layout
        // TODO Add listener and robis

        if (item.javaClass == TVShowTMDB::class.java) {
            // TODO inflate tv show layout
        } else if (item.javaClass == MovieTMDB::class.java) {
            val bottomSheet = inflateLayout(context, layoutInflater, R.layout.movie_review_bottom_sheet)
            setMovieInfo(bottomSheet, item as MovieTMDB)
            val date  = setDatePicker(context, bottomSheet)
        }

        Log.d(TAG, "Eccoci")
    }

    private fun inflateLayout(context: Context, layoutInflater: LayoutInflater, layout  :Int): RoundedBottomSheetDialog {
        val mBottomSheetDialog = RoundedBottomSheetDialog(context)
        val sheetView = layoutInflater.inflate(layout, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()

        return mBottomSheetDialog
    }

    private fun setDatePicker(context : Context, btmDialog: RoundedBottomSheetDialog): GregorianCalendar {
        var date = GregorianCalendar()
        btmDialog.cl_movie_log_date.setOnClickListener {
            val c = Calendar.getInstance()
            val y = c.get(Calendar.YEAR)
            val m = c.get(Calendar.MONTH)
            val d = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        date = GregorianCalendar(year, monthOfYear, dayOfMonth)
                        // TODO set better date format
                        btmDialog.tw_movie_review_date_text.text = date.toString()
                    },
                    y, m, d
            )

            // TODO get better date format

            datePickerDialog.show()
        }

        return date
    }

    private fun setMovieInfo(btmDialog: RoundedBottomSheetDialog, movie: MovieTMDB) {
        btmDialog.findViewById<TextView>(R.id.tw_bs_movie_log_title)?.text =  movie.title
        btmDialog.findViewById<TextView>(R.id.tw_bs_movie_log_resease_date)?.text = getMovieYear(movie.releaseDate)
        btmDialog.findViewById<TextView>(R.id.tw_bs_movie_log_rating)?.text = movie.rating.toString()
    }

    private fun getMovieYear(date: String): String {
        return if (date.isEmpty())
            ""
        else
            date.substring(0,4)
    }

    private fun getDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
        val dateF = Date(date)
        return dateFormat.format(dateF)
    }
}