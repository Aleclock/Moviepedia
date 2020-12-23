package com.example.moviepedia.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviepedia.R
import com.example.moviepedia.adapter.MovieGridAdapter
import com.example.moviepedia.tmdb.Movie
import com.example.moviepedia.tmdb.MoviesRepository
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment() {

    private val TAG = "DiscoverFragment"

    private lateinit var popularMoviesAdapter: MovieGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        Log.d(TAG, "Movies: $movies")
        popularMoviesAdapter.updateMovies(movies)

    }
    private fun onError() {
        Log.d(TAG, "ERROREE")
    }

    private fun initView() {
        recyclerMovies.layoutManager = GridLayoutManager(context, 3)
        popularMoviesAdapter = MovieGridAdapter()

        recyclerMovies.adapter = popularMoviesAdapter

        MoviesRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }
}