package com.example.moviepedia.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.R
import com.example.moviepedia.db.FirestoreUtils
import com.example.moviepedia.model.Movie
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import kotlinx.android.synthetic.main.profile_stats.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup

open class MovieBottomSheet {
    val TAG = "MovieBottomSheet"

    fun createDialog(context: Context, movie: Movie, layoutInflater: LayoutInflater) {
        val mBottomSheetDialog = RoundedBottomSheetDialog(context)
        val sheetView = layoutInflater.inflate(R.layout.movie_bottom_sheet, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()

        setMovieInfo(mBottomSheetDialog, movie)
        manageToggle(mBottomSheetDialog, movie)
        manageSeekbar(mBottomSheetDialog)
    }

    private fun checkInFirestore(toggle: ThemedToggleButtonGroup, movie: Movie) {
        val firestoreUtils = FirestoreUtils()

        firestoreUtils.isInWatchlist(LoginActivity.getUser(), movie, object : FirestoreUtils.FirestorePresenceCallback {
            override fun onCallback(value: MutableMap<String, Any?>) {
                if (value["presence"] as Boolean)
                    toggle.selectButtonWithAnimation(R.id.toggle_sheet_btn_movie_watchlist)
            }
        })

        firestoreUtils.isInWatched(LoginActivity.getUser(), movie, object : FirestoreUtils.FirestorePresenceCallback {
            override fun onCallback(value: MutableMap<String, Any?>) {
                if (value["presence"] as Boolean)
                    toggle.selectButtonWithAnimation(R.id.toggle_sheet_btn_movie_watched)
            }

        })
    }

    private fun manageToggle(mBottomSheetDialog: RoundedBottomSheetDialog, movie: Movie) {
        val toggle = mBottomSheetDialog.findViewById<ThemedToggleButtonGroup>(R.id.toggle_group_movie_sheet)
        toggle?.setOnSelectListener { button: ThemedButton ->
            val firestoreUtils = FirestoreUtils()

            when (button.id) {
                R.id.toggle_sheet_btn_movie_watchlist -> {
                    if (button.isSelected) {
                        firestoreUtils.addMovieToWatchlist(LoginActivity.getUser(), movie)
                        firestoreUtils.updateUserStats("watchlist")
                    } else {
                        firestoreUtils.removeMovieToWatchlist(LoginActivity.getUser(), movie)
                        firestoreUtils.updateUserStats("watchlist")
                    }
                }
                R.id.toggle_sheet_btn_movie_watched -> {
                    if (button.isSelected) {
                        firestoreUtils.addMovieToWatched(LoginActivity.getUser(), movie)
                        firestoreUtils.updateUserStats("movies")
                    } else {
                        firestoreUtils.removeMovieToWatched(LoginActivity.getUser(), movie)
                        firestoreUtils.updateUserStats("watchlist")
                    }
                }
            }
        }

        checkInFirestore(toggle!!, movie)
    }

    private fun manageSeekbar(mBottomSheetDialog: RoundedBottomSheetDialog) {
        val seekbar = mBottomSheetDialog.findViewById<RangeSeekBar>(R.id.range_seekbar)
        seekbar?.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                //TODO("Not yet implemented")
            }

            override fun onRangeChanged(
                    view: RangeSeekBar?,
                    leftValue: Float,
                    rightValue: Float,
                    isFromUser: Boolean
            ) {
                Log.d(TAG, leftValue.toString())
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                //TODO("Not yet implemented")
            }
        })
    }

    private fun setMovieInfo(mBottomSheetDialog: RoundedBottomSheetDialog, movie: Movie) {
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_movie_title)?.text =  movie.title
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_movie_resease_date)?.text = movie.releaseDate
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_movie_rating)?.text = movie.rating.toString()
    }
}