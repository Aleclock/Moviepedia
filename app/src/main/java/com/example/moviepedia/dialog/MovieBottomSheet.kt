package com.example.moviepedia.dialog

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.R
import com.example.moviepedia.activity.MovieActivity
import com.example.moviepedia.db.FirestoreUtils
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.model.WatchedItem
import com.google.gson.Gson
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup


open class MovieBottomSheet {
    val TAG = "MovieBottomSheet"

    fun createDialog(context: Context, movie: MovieTMDB, layoutInflater: LayoutInflater) {
        val mBottomSheetDialog = RoundedBottomSheetDialog(context)
        val sheetView = layoutInflater.inflate(R.layout.movie_bottom_sheet, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()

        manageButtons(context, mBottomSheetDialog, movie)
        setMovieInfo(mBottomSheetDialog, movie)
        manageToggle(mBottomSheetDialog, movie)
    }

    private fun manageButtons(context: Context, mBottomSheetDialog: RoundedBottomSheetDialog, movie: MovieTMDB) {

        mBottomSheetDialog.findViewById<TextView>(R.id.tw_review_log)!!.setOnClickListener {
            mBottomSheetDialog.dismiss()
            ItemLogBottomSheet().createDialog(context, movie, LayoutInflater.from(context))
            //val intent = Intent(context, SettingsActivity::class.java)
            //intent.putExtra()
            //startActivity(intent)
        }

        mBottomSheetDialog.findViewById<TextView>(R.id.tw_add_to_list)!!.setOnClickListener {
            // TODO
        }

        mBottomSheetDialog.findViewById<TextView>(R.id.tw_go_to_film)!!.setOnClickListener {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra("movie", Gson().toJson(movie))
            startActivity(context,intent,null)
            mBottomSheetDialog.dismiss()
        }
    }

    private fun checkInFirestore(mBottomSheetDialog: RoundedBottomSheetDialog, toggle: ThemedToggleButtonGroup, movie: MovieTMDB) {
        val firestoreUtils = FirestoreUtils()

        firestoreUtils.isInWatchlist(LoginActivity.getUser(), movie, object : FirestoreUtils.FirestorePresenceCallback {
            override fun onCallback(value: MutableMap<String, Any?>) {
                if (value["presence"] as Boolean)
                    toggle.selectButtonWithAnimation(R.id.toggle_sheet_btn_movie_watchlist)
            }
        })

        firestoreUtils.isInWatched(LoginActivity.getUser(), movie, object : FirestoreUtils.FirestorePresenceCallback {
            override fun onCallback(value: MutableMap<String, Any?>) {
                if (value["presence"] as Boolean) {
                    toggle.selectButtonWithAnimation(R.id.toggle_sheet_btn_movie_watched)
                    val watchedItem = value["item"] as WatchedItem
                    manageSeekbar(mBottomSheetDialog, watchedItem.id, watchedItem.rating)
                } else
                    setSeekbarInvisible(mBottomSheetDialog)
            }

        })
    }

    private fun manageToggle(mBottomSheetDialog: RoundedBottomSheetDialog, movie: MovieTMDB) {
        val toggle = mBottomSheetDialog.findViewById<ThemedToggleButtonGroup>(R.id.toggle_group_movie_sheet)

        checkInFirestore(mBottomSheetDialog, toggle!!, movie)
        toggle.setOnSelectListener { button: ThemedButton ->
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
                        manageSeekbar(mBottomSheetDialog, movie.id, null)
                    } else {
                        firestoreUtils.removeMovieToWatched(LoginActivity.getUser(), movie)
                        firestoreUtils.updateUserStats("watchlist")
                        setSeekbarInvisible(mBottomSheetDialog)
                    }
                }
            }
        }
    }

    // TODO aggiungere animazioni di entrata/uscita del seekbar
    private fun setSeekbarInvisible(mBottomSheetDialog: RoundedBottomSheetDialog) {
        val seekbar = mBottomSheetDialog.findViewById<RangeSeekBar>(R.id.range_seekbar)
        seekbar?.visibility= View.INVISIBLE
    }

    private fun setSeekbarVisible (mBottomSheetDialog: RoundedBottomSheetDialog) {
        val seekbar = mBottomSheetDialog.findViewById<RangeSeekBar>(R.id.range_seekbar)
        seekbar?.visibility= View.VISIBLE
    }

    private fun manageSeekbar(mBottomSheetDialog: RoundedBottomSheetDialog, movie_id : Long, rating: Int?) {
        val seekbar = mBottomSheetDialog.findViewById<RangeSeekBar>(R.id.range_seekbar)
        setSeekbarVisible(mBottomSheetDialog)

        if (rating != null)
            seekbar?.setProgress(rating.toFloat())

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
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                FirestoreUtils().updateWatchedItem(LoginActivity.getUser(), movie_id , "rating", view?.leftSeekBar!!.progress)
            }

        })
    }

    private fun setMovieInfo(mBottomSheetDialog: RoundedBottomSheetDialog, movie: MovieTMDB) {
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_bs_movie_title)?.text =  movie.title
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_bs_movie_resease_date)?.text = getMovieYear(movie.releaseDate)
        mBottomSheetDialog.findViewById<TextView>(R.id.tw_bs_movie_rating)?.text = movie.rating.toString()
    }

    private fun getMovieYear(date: String): String {
        return if (date.isEmpty())
            ""
        else
            date.substring(0,4)
    }
}