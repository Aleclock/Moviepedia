package com.example.moviepedia.activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.moviepedia.R
import com.example.moviepedia.model.GenreTMDB
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.tmdb.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.tw_movie_title
import kotlinx.android.synthetic.main.item_detail_info_body.*
import kotlinx.android.synthetic.main.item_detail_overview_body.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup
import java.security.AccessController.getContext
import java.text.NumberFormat
import java.util.*


class MovieActivity : AppCompatActivity() {

    val TAG = "MovieActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val json_movie = intent.getStringExtra("movie")
        val movie = Gson().fromJson(json_movie, MovieTMDB::class.java)

        setBackButton()
        setPrincipalInfo(movie)
        MoviesRepository.getMovieDetail(
            movie,
            onSuccess = :: onMovieDetailFetched,
            onError = ::onError
        )

        MoviesRepository.getMovieCredits(
            movie,
            onSuccess = :: onMovieCreditsFetched,
            onError = ::onError
        )

        MoviesRepository.getSimilariMovies(
                movie,
                onSuccess =  ::onSimilariMovieFetched,
                onError = ::onError
        )

        MoviesRepository.getMovieProviders(
                movie,
                onSuccess = ::onMovieProvidersFetched,
                onError = ::onError
        )

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
        tw_movie_runtime.text = "${movieDetail.runtime} min"
        info_row_original_title.text = movieDetail.original_title
        original_language.text = getCountryName(movieDetail.original_language)
        info_row_budget.text = getCurrencyValue(movieDetail.budget.toString())
        info_row_revenue.text = getCurrencyValue(movieDetail.revenue.toString())
        info_row_production_companies.text = movieDetail.production_companies.map { it.name }.joinToString(", ")

    }

    // TODO trovare soluzione per dimensione toggle
    private fun setToggleButtons(genres: List<GenreTMDB>) {
        genres.forEach {
            val toggleButton = ThemedButton(window.context)
            toggleButton.text = it.name

            toggleButton.bgColor = resources.getColor(R.color.black)
            toggleButton.selectedBgColor = resources.getColor(R.color.black)
            toggleButton.borderColor = resources.getColor(R.color.white)
            toggleButton.selectedBorderColor = resources.getColor(R.color.white)
            toggleButton.borderWidth = 5f
            toggleButton.setPadding(0,0,0,0)
            toggleButton.selectedBorderWidth = 5f
            toggleButton.textColor = resources.getColor(R.color.white)
            toggleButton.applyToTexts { it.textSize = 12f }
            toggleButton.applyToCards {  RelativeLayout.LayoutParams(MATCH_PARENT, dpToPixels(30f))  }
            toggleButton.gravity = Gravity.CENTER_VERTICAL

            /*<nl.bryanderidder.themedtogglebuttongroup.ThemedButton
            android:id="@+id/toggle_profile_btn_watchlist"
            android:layout_width="wrap_content"
            android:layout_height="30dp"
            app:toggle_textSize="@dimen/text_size_small"
            app:toggle_text="@string/watchlist"
            app:toggle_textColor="@color/white"
            app:toggle_backgroundColor="@color/black"
            app:toggle_borderWidth = "2dp"
            app:toggle_selectedBorderWidth = "2dp"
            app:toggle_borderColor="@color/white"
            app:toggle_selectedBorderColor = "@color/fireOpal"
            app:toggle_selectedBackgroundColor = "@color/fireOpal"/>*/

            //val params = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val params = ConstraintLayout.LayoutParams(WRAP_CONTENT, dpToPixels(30f))
            params.setMargins(5,5,5,5)
            item_detail_toggle_group.addView(toggleButton, params)
            //https://stackoverflow.com/questions/29028168/how-to-pass-attributeset-when-creating-view-programmatically-in-android
        }
    }

    private fun setBackButton() {
        item_detail_btn_back.setOnClickListener {
            super.finish()
        }
    }

    private fun onMovieDetailFetched(detail: GetMovieDetailResponse) {
        setToggleButtons(detail.genres)
        setDetailedInfo(detail)
        Log.d(TAG, "Movie detail " + detail.toString())


        // TODO aggiornare tabella info
    }

    private fun onMovieProvidersFetched(providers: GetMovieProvidersResponse) {
        Log.d(TAG, "Movie providers " + providers.results.IT.flatrate)

        // TODO retrieve providers info
    }

    private fun onMovieCreditsFetched(credits: GetMovieCreditsResponse) {
        Log.d(TAG, "Movie credits " + credits.toString())
        // TODO aggiornare crew layout e inserire director
    }

    private fun onSimilariMovieFetched(movies: List<MovieTMDB>) {
        Log.d(TAG, "Similar movies: " + movies)
        if (movies.isEmpty()) {
            // TODO scrivere messaggio "Non ci sono film simili"
        } else {
            // update horizontal view
        }
    }

    private fun onError() {
        Log.d(TAG, "ERROREE")
    }

    private fun getMovieYear(date: String): String {
        return date.substring(0,4)
    }

    // TODO animazioni https://github.com/SmartToolFactory/Animation-Tutorials
    private fun setTabListener() {
        tw_item_detail_overview_title.setOnClickListener { setVisibility(include_detail_body_overview)}
        tw_item_detail_stats_title.setOnClickListener { setVisibility(include_detail_body_stats)}
        tw_item_detail_info_title.setOnClickListener { setVisibility(include_detail_body_info) }
        tw_item_detail_cast_title.setOnClickListener {}
        tw_item_detail_related_title.setOnClickListener {}
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
        val pixel = (fpixels + 0.5f).toInt()
        return pixel
    }

    private fun getCurrencyValue(value: String): String {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")
        return format.format(value.toInt())
    }
}