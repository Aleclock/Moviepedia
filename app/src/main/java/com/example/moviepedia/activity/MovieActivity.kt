package com.example.moviepedia.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.moviepedia.R
import com.example.moviepedia.model.Movie
import com.example.moviepedia.model.TVShow
import com.example.moviepedia.tmdb.GetMovieCreditsResponse
import com.example.moviepedia.tmdb.GetMovieDetailResponse
import com.example.moviepedia.tmdb.MoviesRepository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.item_detail_overview_body.*


class MovieActivity : AppCompatActivity() {

    val TAG = "MovieActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val json_movie = intent.getStringExtra("movie")
        val movie = Gson().fromJson(json_movie, Movie::class.java)

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

        setTabListener()

        tw_movie_title.text =  movie.title
        tw_movie_resease_date.text = getMovieYear(movie.releaseDate)

        tw_item_detail_overview_body.text = movie.overview
    }

    private fun onMovieDetailFetched(detail: GetMovieDetailResponse) {
        // TODO aggiornare tabella info
    }

    private fun onMovieCreditsFetched(credits: GetMovieCreditsResponse) {
        // TODO aggiornare crew layout e inserire director
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
        tw_item_detail_cast_title.setOnClickListener {}
        tw_item_detail_related_title.setOnClickListener {}
    }

    private fun setVisibility(v: View) {
        when (v.visibility) {
            View.GONE    -> v.visibility = View.VISIBLE
            View.VISIBLE -> v.visibility = View.GONE
        }
    }
}