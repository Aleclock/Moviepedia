package com.example.moviepedia.utils
import android.view.View
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar

class SliderRatingBar {
  val TAG = "SliderRatingBar"
  var ratingValue = 0f

  fun initRatingBar(seekBar: RangeSeekBar) {
    seekBar.setOnRangeChangedListener(object : OnRangeChangedListener {
      override fun onStartTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {}

      override fun onRangeChanged(view: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
      }

      override fun onStopTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {
        ratingValue = view.leftSeekBar!!.progress
      }
    })
  }

  fun getProgress(): Float? {
    return if (ratingValue == 0f)
      null
    else
      ratingValue
  }

  fun setProgress(seekBar: RangeSeekBar, rating: Float) {
    seekBar.setProgress(rating)
  }

  fun setSeekBarVisible(seekBar: RangeSeekBar) {
    seekBar.visibility= View.VISIBLE
  }

  fun setSeekBarInvisible(seekBar: RangeSeekBar) {
    seekBar.visibility = View.INVISIBLE
  }
}