package com.example.moviepedia.activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.moviepedia.R
import com.example.moviepedia.adapter.CastGridHorizontalAdapter
import com.example.moviepedia.adapter.MovieGridHorizontalAdapter
import com.example.moviepedia.model.GenreTMDB
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.tmdb.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.tw_movie_title
import kotlinx.android.synthetic.main.fragment_discover.recyclerMovies
import kotlinx.android.synthetic.main.item_detail_cast_body.*
import kotlinx.android.synthetic.main.item_detail_info_body.*
import kotlinx.android.synthetic.main.item_detail_overview_body.*
import kotlinx.android.synthetic.main.item_detail_related_movie_body.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import java.text.NumberFormat
import java.util.*


class MovieActivity : AppCompatActivity() {

  val TAG = "MovieActivity"

  private lateinit var similarMoviesAdapter: MovieGridHorizontalAdapter
  private lateinit var castMovieAdapter: CastGridHorizontalAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie)

    val jsonMovie = intent.getStringExtra("movie")
    val movie = Gson().fromJson(jsonMovie, MovieTMDB::class.java)

    setBackButton()
    setPrincipalInfo(movie)
    initCastMovieView(movie)
    initSimilarMoviesView(movie)

    MoviesRepository.getMovieDetail(
      movie,
      onSuccess = :: onMovieDetailFetched,
      onError = ::onError
    )

    /*TODO decide if add or not movie providers
    MoviesRepository.getMovieProviders(
            movie,
            onSuccess = ::onMovieProvidersFetched,
            onError = ::onError
    )*/

    setTabListener()
  }

  private fun setPrincipalInfo(movie: MovieTMDB) {
    tw_movie_title.text =  movie.title
    tw_movie_resease_date.text = getMovieYear(movie.releaseDate)
    tw_item_detail_overview_body.text = movie.overview

    Glide.with(window.decorView.rootView)
      .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
      .fitCenter()
      .into(movie_detail_imageview)
  }

  private fun setDetailedInfo(movieDetail: GetMovieDetailResponse) {
    val na = "N.A." //"•"

    if (movieDetail.runtime != 0)
      tw_movie_runtime.text = "${movieDetail.runtime} min"

    info_row_original_title.text = movieDetail.original_title
    original_language.text = getCountryName(movieDetail.original_language)
    if (getCurrencyValue(movieDetail.budget.toString()) != getCurrencyValue("0"))
      info_row_budget.text = getCurrencyValue(movieDetail.budget.toString())
    else
      info_row_budget.text = na

    if (getCurrencyValue(movieDetail.revenue.toString()) != getCurrencyValue("0"))
      info_row_revenue.text = getCurrencyValue(movieDetail.revenue.toString())
    else
      info_row_revenue.text = na

    info_row_production_companies.text = movieDetail.production_companies.joinToString(", ") { it.name }

  }

  private fun setToggleButtons(genres: List<GenreTMDB>) {
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

      val params = ConstraintLayout.LayoutParams(WRAP_CONTENT, dpToPixels(30f))
      params.setMargins(5,5,5,5)
      item_detail_toggle_group.addView(toggleButton, params)
    }
  }

  private fun setBackButton() {
    item_detail_btn_back.setOnClickListener {
      super.finish()
    }
  }

  private fun initCastMovieView(movie: MovieTMDB) {
    recyclerItemDetailCast.layoutManager = GridLayoutManager(window.context,1, GridLayoutManager.HORIZONTAL, false)
    castMovieAdapter = CastGridHorizontalAdapter(window.context, layoutInflater)

    recyclerItemDetailCast.adapter = castMovieAdapter

    MoviesRepository.getMovieCredits(
      movie,
      onSuccess = :: onMovieCreditsFetched,
      onError = ::onError
    )
  }

  private fun initSimilarMoviesView(movie: MovieTMDB) {
    recyclerItemDetailSimilar.layoutManager = GridLayoutManager(window.context,1, GridLayoutManager.HORIZONTAL, false)
    similarMoviesAdapter = MovieGridHorizontalAdapter(window.context, layoutInflater)

    recyclerItemDetailSimilar.adapter = similarMoviesAdapter

    MoviesRepository.getSimilariMovies(
      movie,
      onSuccess =  ::onSimilariMovieFetched,
      onError = ::onError
    )
  }

  private fun onMovieDetailFetched(detail: GetMovieDetailResponse) {
    setToggleButtons(detail.genres)
    setDetailedInfo(detail)
    //Log.d(TAG, "Movie detail $detail")
  }

  private fun onMovieProvidersFetched(providers: GetMovieProvidersResponse) {
    Log.d(TAG, "Movie providers " + providers.results.IT.flatrate)
    // TODO retrieve providers info
  }

  private fun onMovieCreditsFetched(credits: GetMovieCreditsResponse) {
    val directors = credits.crew.filter {it.job == "Director"}
    val directorsNames = directors.map { it.name }
    tw_movie_director.text = let { directorsNames.joinToString(", ") }

    if (credits.cast.isEmpty()) {
      tv_item_detail_cast_error.visibility = View.VISIBLE
    } else {
      castMovieAdapter.updateCast(credits.cast)
    }
  }

  private fun onSimilariMovieFetched(movies: List<MovieTMDB>) {
    if (movies.isEmpty()) {
      tv_item_detail_similar_movies_error.visibility = View.VISIBLE
    } else {
      similarMoviesAdapter.updateMovies(movies)
    }
  }

  private fun onError() {
    Log.d(TAG, "Errore Movie Activity")
  }

  private fun getMovieYear(date: String): String {
    return date.substring(0,4)
  }

  // TODO animazioni https://github.com/SmartToolFactory/Animation-Tutorials
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

  private fun getCountryName(countryName: String) : String? {
    return Locale(countryName).getDisplayLanguage(Locale.ENGLISH)
  }

  private fun dpToPixels(dp: Float): Int {
    val metrics: DisplayMetrics = resources.displayMetrics
    val fpixels = metrics.density * dp
    return (fpixels + 0.5f).toInt()
  }

  private fun getCurrencyValue(value: String): String {
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("USD")
    return format.format(value.toInt())
  }


  /**
   * extension function for padding
   */
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