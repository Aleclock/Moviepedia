package com.example.moviepedia.activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.moviepedia.R
import com.example.moviepedia.adapter.CastGridHorizontalAdapter
import com.example.moviepedia.adapter.MovieGridHorizontalAdapter
import com.example.moviepedia.adapter.TVShowGridHorizontalAdapter
import com.example.moviepedia.model.*
import com.example.moviepedia.tmdb.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tv_show.item_detail_btn_back
import kotlinx.android.synthetic.main.activity_tv_show.item_detail_toggle_group
import kotlinx.android.synthetic.main.activity_tv_show.*
import kotlinx.android.synthetic.main.activity_tv_show.include_detail_body_cast
import kotlinx.android.synthetic.main.activity_tv_show.include_detail_body_info
import kotlinx.android.synthetic.main.activity_tv_show.include_detail_body_overview
import kotlinx.android.synthetic.main.activity_tv_show.include_detail_body_related_movies
import kotlinx.android.synthetic.main.activity_tv_show.include_detail_body_stats
import kotlinx.android.synthetic.main.activity_tv_show.tw_item_detail_cast_title
import kotlinx.android.synthetic.main.activity_tv_show.tw_item_detail_info_title
import kotlinx.android.synthetic.main.activity_tv_show.tw_item_detail_overview_title
import kotlinx.android.synthetic.main.activity_tv_show.tw_item_detail_related_title
import kotlinx.android.synthetic.main.activity_tv_show.tw_item_detail_stats_title
import kotlinx.android.synthetic.main.item_detail_cast_body.*
import kotlinx.android.synthetic.main.item_detail_info_body.info_row_original_title
import kotlinx.android.synthetic.main.item_detail_info_body.info_row_production_companies
import kotlinx.android.synthetic.main.item_detail_info_body.original_language
import kotlinx.android.synthetic.main.item_detail_overview_body.*
import kotlinx.android.synthetic.main.item_detail_related_movie_body.*
import kotlinx.android.synthetic.main.item_tvshow_detail_info_body.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import java.util.*

class TVShowActivity : AppCompatActivity() {

  val TAG = "TVShowActivity__"
  private lateinit var castTVShowAdapter: CastGridHorizontalAdapter
  private lateinit var similarTVShowAdapter: TVShowGridHorizontalAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_tv_show)

    val jsonTvShow = intent.getStringExtra("tvshow")
    val tvShow = Gson().fromJson(jsonTvShow, TVShowTMDB::class.java)
    setBackButton()
    setPrincipalInfo(tvShow)

    MoviesRepository.getTVShowDetail(
      tvShow,
      onSuccess = :: onTvShowDetailFetched,
      onError = ::onError
    )

    initCastTVShowView(tvShow)
    initSimilarTVShowView(tvShow)
    setTabListener()
  }

  private fun initSimilarTVShowView(tvShow: TVShowTMDB) {
    recyclerItemDetailSimilar.layoutManager = GridLayoutManager(window.context,1, GridLayoutManager.HORIZONTAL, false)
    similarTVShowAdapter = TVShowGridHorizontalAdapter(window.context, layoutInflater)
    recyclerItemDetailSimilar.adapter = similarTVShowAdapter
    MoviesRepository.getSimilarTVShow(
        tvShow,
        onSuccess =  :: onSimilariTVShowFetched,
        onError = :: onError
    )
  }

  private fun initCastTVShowView(tvShow: TVShowTMDB) {
    recyclerItemDetailCast.layoutManager = GridLayoutManager(window.context,1, GridLayoutManager.HORIZONTAL, false)
    castTVShowAdapter = CastGridHorizontalAdapter(window.context, layoutInflater)
    recyclerItemDetailCast.adapter = castTVShowAdapter
    MoviesRepository.getTVShowCredits(
        tvShow,
        onSuccess = :: onTVShowCreditsFetched,
        onError = ::onError
    )
  }

  private fun onTVShowCreditsFetched(credits: GetMovieCreditsResponse) {
    if (credits.cast.isEmpty()) {
      tv_item_detail_cast_error.visibility = View.VISIBLE
    } else {
      castTVShowAdapter.updateCast(credits.cast)
    }
  }

  private fun onSimilariTVShowFetched (tvShow: List<TVShowTMDB>) {
    if (tvShow.isEmpty()) {
      tv_item_detail_similar_movies_error.visibility = View.VISIBLE
    } else {
      similarTVShowAdapter.updateMovies(tvShow)
    }
  }

  private fun onTvShowDetailFetched(detail: GetTVShowDetailResponse) {
    setToggleGenresButtons(detail.genres)
    setToggleSeasonsButtons(detail.id, detail.seasons)
    setDetailedInfo(detail)
    Log.d(TAG, "Movie detail $detail")
  }

  private fun onTvSeasonFetched(season : GetTVSeasonsResponse) {
    Log.d(TAG, season.toString())
  }

  private fun onError() {
    Log.d(TAG, "ERROREE")
  }

  private fun setToggleSeasonsButtons(tv_id: Int, seasons: List<SeasonsTMDB>) {
    val borderWidth = 5f

    seasons.forEach {
      val seasonNumber = it.season_number
      val toggleButton = ThemedButton(window.context)
      toggleButton.text = it.name
      toggleButton.applyToTexts { text ->
        text.textSize = 12f
        text.setViewPadding(all = 20f)
        text.layoutGravity = Gravity.CENTER_VERTICAL
      }

      toggleButton.borderWidth = borderWidth
      toggleButton.selectedBorderWidth = borderWidth
      toggleButton.bgColor = ContextCompat.getColor(window.context, R.color.black)
      toggleButton.selectedBgColor = ContextCompat.getColor(window.context, R.color.fireOpal)
      toggleButton.textColor = ContextCompat.getColor(window.context, R.color.white)
      toggleButton.borderColor = ContextCompat.getColor(window.context, R.color.white)
      toggleButton.selectedBorderColor = ContextCompat.getColor(window.context, R.color.fireOpal)

      toggleButton.setOnClickListener {
        MoviesRepository.getTVSeasons(
          tv_id,
          seasonNumber,
          onSuccess = :: onTvSeasonFetched,
          onError = ::onError
        )
      }

      val params = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dpToPixels(30f))
      params.setMargins(5,5,5,5)
      item_detail_toggle_group_seasons.addView(toggleButton, params)
    }
  }

  private fun setToggleGenresButtons(genres: List<GenreTMDB>) {
    val borderWidth = 5f

    genres.forEach {
      val toggleButton = ThemedButton(window.context)
      toggleButton.text = it.name
      toggleButton.applyToTexts {text ->
        text.textSize = 12f
        text.setViewPadding(all = 20f)
        text.layoutGravity = Gravity.CENTER_VERTICAL
      }

      toggleButton.borderWidth = borderWidth
      toggleButton.selectedBorderWidth = borderWidth
      toggleButton.bgColor = ContextCompat.getColor(window.context, R.color.darkestGray)
      toggleButton.selectedBgColor = ContextCompat.getColor(window.context, R.color.darkestGray)
      toggleButton.textColor = ContextCompat.getColor(window.context, R.color.white)
      toggleButton.borderColor = ContextCompat.getColor(window.context, R.color.darkestGray)
      toggleButton.selectedBorderColor = ContextCompat.getColor(window.context, R.color.darkestGray)

      val params = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dpToPixels(30f))
      params.setMargins(5,5,5,5)
      item_detail_toggle_group.addView(toggleButton, params)
    }
  }

  private fun setDetailedInfo(detail: GetTVShowDetailResponse) {
    val runtime = detail.episode_run_time.average()
    if (runtime.toFloat() != 0f)
      tw_tvshow_runtime.text = "${runtime} min"

    info_row_original_title.text = detail.original_name
    original_language.text = getCountryName(detail.original_language)

    info_row_seasons.text = detail.number_of_seasons.toString()
    info_row_episodes.text = detail.number_of_episodes.toString()
    info_row_in_production.text = detail.in_production.toString()

    info_row_production_companies.text = detail.production_companies.joinToString(", ") { it.name }

    // TODO aggiornare detail
  }

  private fun setBackButton() {
    item_detail_btn_back.setOnClickListener {
      super.finish()
    }
  }

  private fun setTabListener() {
    tw_item_detail_overview_title.setOnClickListener { setVisibility(include_detail_body_overview)}
    tw_item_detail_stats_title.setOnClickListener { setVisibility(include_detail_body_stats)}
    tw_item_detail_info_title.setOnClickListener { setVisibility(include_detail_body_info) }
    tw_item_detail_cast_title.setOnClickListener { setVisibility(include_detail_body_cast)}
    tw_item_detail_related_title.setOnClickListener { setVisibility(include_detail_body_related_movies)}
  }

  private fun setVisibility(v: View) {
    when (v.visibility) {
      View.GONE    -> v.visibility = View.VISIBLE
      View.VISIBLE -> v.visibility = View.GONE
    }
  }

  private fun getMovieYear(date: String): String {
    return date.substring(0,4)
  }

  private fun setPrincipalInfo(tvShow: TVShowTMDB) {
    tw_tvshow_title.text = tvShow.name
    tw_tvshow_resease_date.text = getMovieYear(tvShow.first_air_date)
    tw_item_detail_overview_body.text = tvShow.overview

    Glide.with(window.decorView.rootView)
      .load("https://image.tmdb.org/t/p/w342${tvShow.posterPath}")
      .fitCenter()
      .into(tvshow_detail_imageview)
  }

  private fun dpToPixels(dp: Float): Int {
    val metrics: DisplayMetrics = resources.displayMetrics
    val fpixels = metrics.density * dp
    return (fpixels + 0.5f).toInt()
  }

  private fun getCountryName(countryName: String) : String? {
    return Locale(countryName).getDisplayLanguage(Locale.ENGLISH)
  }

  // extension function for padding
  private fun View.setViewPadding(
    left: Float? = null, top: Float? = null,
    right: Float? = null, bottom: Float? = null,
    horizontal: Float? = null, vertical: Float? = null,
    all: Float? = null
  ) {
    if (listOfNotNull(left, top, right, bottom, horizontal, vertical, all).any { it < 0f }) return
    all?.let { setPadding(it.toInt(), it.toInt(), it.toInt(), it.toInt()) }
    horizontal?.let { setPadding(paddingLeft, it.toInt(), paddingRight, it.toInt()) }
    vertical?.let { setPadding(it.toInt(), paddingTop, it.toInt(), paddingBottom) }
    setPadding(
      left?.toInt() ?: paddingLeft,
      top?.toInt() ?: paddingTop,
      right?.toInt() ?: paddingRight,
      bottom?.toInt() ?: paddingBottom
    )
  }

  // extension property for layout gravity
  private var View.layoutGravity
    get() = (layoutParams as FrameLayout.LayoutParams).gravity
    set(value) {
      layoutParams = FrameLayout.LayoutParams(
        layoutParams.width,
        layoutParams.height,
        value
    )
  }
}