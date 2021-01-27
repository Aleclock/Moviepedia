package com.example.moviepedia.utils

import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepedia.activity.TVShowActivity
import com.example.moviepedia.adapter.EpisodesAdapter


class SwipeEpisodeController(var adapter: EpisodesAdapter, var tvShowActivity: TVShowActivity) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
  val TAG = "SwipeEpisodeController"
  private var swipeBack = false
  private val SWIPE_RIGHT = 200

  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder
  ): Boolean {
    return false
  }

  override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
    viewHolder.itemView.translationX = 0f
    super.clearView(recyclerView, viewHolder)
  }

  override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
    if (swipeBack) {
      swipeBack = false
      return 0
    }

    return super.convertToAbsoluteDirection(flags, layoutDirection)
  }

  override fun onChildDraw(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    if (actionState == ACTION_STATE_SWIPE) {
      setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    super.onChildDraw(c, recyclerView, viewHolder,  dX, dY, actionState, isCurrentlyActive)
  }

  private fun setTouchListener(
    c:Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean) {
    recyclerView.setOnTouchListener { v, event ->
      swipeBack = event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP

      if (swipeBack) {
        if (dX > SWIPE_RIGHT) {
          //adapter.updateWatched(viewHolder.adapterPosition)
          tvShowActivity.onSelectedItem(viewHolder.adapterPosition)
        }
      }

      false
    }

      swipeBack = false
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    /*val pos = viewHolder.adapterPosition
    adapter.updateWatched(pos)*/
  }
}