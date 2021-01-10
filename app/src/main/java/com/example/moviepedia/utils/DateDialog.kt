package com.example.moviepedia.utils

import android.app.DatePickerDialog
import android.content.Context
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.movie_review_bottom_sheet.*
import java.text.SimpleDateFormat
import java.util.*

class DateDialog {
    val TAG = "DateDialog"
    var date: Calendar? = null

    fun initDatePicker(context : Context, btmDialog: RoundedBottomSheetDialog) {
        var cal = Calendar.getInstance()

        btmDialog.cl_movie_log_date.setOnClickListener {
            val c = Calendar.getInstance()
            val y = c.get(Calendar.YEAR)
            val m = c.get(Calendar.MONTH)
            val d = c.get(Calendar.DAY_OF_MONTH)

            var datePickerDialog = DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.YEAR, year)

                        btmDialog.tw_movie_review_date_text.text = getFormattedDate(cal)
                        date = cal
                    },
                    y, m, d
            ).also {

                it.show()
            }
        }
    }

    private fun getFormattedDate(cal: Calendar): String {
        // TODO capire se cambiare Locale
        val dateString = SimpleDateFormat( "EEE, MMM d yyyy", Locale.ENGLISH)
        return dateString.format(cal.time)
    }

    fun getSelectedDate(): Calendar? {
        return date
    }
}