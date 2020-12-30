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
import com.example.moviepedia.adapter.TVShowGridAdapter
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.tmdb.MoviesRepository
import com.example.moviepedia.model.TVShowTMDB
import kotlinx.android.synthetic.main.fragment_discover.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

class DiscoverFragment : Fragment() {

    private val TAG = "DiscoverFragment"

    private lateinit var popularMoviesAdapter: MovieGridAdapter
    private lateinit var popularTVShowAdapter: TVShowGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToggleGroup()
    }

    private fun onPopularMoviesFetched(movies: List<MovieTMDB>) {
        popularMoviesAdapter.updateMovies(movies)
    }

    private fun onPopularTVShowsFetched(tvShows: List<TVShowTMDB>) {
        popularTVShowAdapter.updateTVShows(tvShows)
    }
    private fun onError() {
        Log.d(TAG, "ERROREE")
    }

    private fun initPopularMoviesView() {
        recyclerMovies.layoutManager = GridLayoutManager(context, 3)
        popularMoviesAdapter = context?.let { MovieGridAdapter(it, layoutInflater) }!!

        recyclerMovies.adapter = popularMoviesAdapter

        MoviesRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    private fun initPopularTVShowView() {
        recyclerMovies.layoutManager = GridLayoutManager(context, 3)
        popularTVShowAdapter = context?.let { TVShowGridAdapter(it) }!!

        recyclerMovies.adapter = popularTVShowAdapter

        MoviesRepository.getPopularTVShow(
            onSuccess = ::onPopularTVShowsFetched,
            onError = ::onError
        )
    }

    private fun initToggleGroup() {
        val allButtons = toggle_group_discover.buttons
        toggle_group_discover.selectButton(allButtons[0].id)
        initPopularMoviesView()

        toggle_group_discover.setOnSelectListener { button: ThemedButton ->
            when (button.id) {
                R.id.toggle_btn_popularMovies -> {
                    initPopularMoviesView()
                }
                R.id.toggle_btn_popularTVshow -> {
                    initPopularTVShowView()
                }
                R.id.toggle_btn_popularlists -> {
                    Log.d(TAG, "Popular lists")
                }
            }
        }
    }
}