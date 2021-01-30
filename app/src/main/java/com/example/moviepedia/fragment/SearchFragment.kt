package com.example.moviepedia.fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviepedia.R
import com.example.moviepedia.adapter.ItemGridAdapter
import com.example.moviepedia.adapter.MovieGridAdapter
import com.example.moviepedia.adapter.TVShowGridAdapter
import com.example.moviepedia.model.MovieTMDB
import com.example.moviepedia.tmdb.MoviesRepository
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_search.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import java.util.*

class SearchFragment : Fragment() {
  val TAG = "SearchFragment"

  var queryString = ""
  lateinit var queryType : String
  private lateinit var itemAdapter: ItemGridAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_search, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initRecylerView()
    initToggleGroup()
    initSearchView()
  }

  private fun initToggleGroup() {
    val allButtons = toggle_group_search.buttons
    toggle_group_search.selectButton(allButtons[0].id)
    queryType = allButtons[0].text

    toggle_group_search.setOnSelectListener { button: ThemedButton ->
      queryType = button.text
      if (queryString != "") {
        when (queryType) {
          "Movie" -> {
            MoviesRepository.searchMovie(
              query = queryString,
              onSuccess = ::onItemsFetched,
              onError = ::onError
            )
          }

          "TV shows" -> {
            MoviesRepository.searchTVShow(
              query = queryString,
              onSuccess = ::onItemsFetched,
              onError = ::onError
            )
          }
        }
      }
    }
  }

  private fun initRecylerView() {
    recycler_search.layoutManager = GridLayoutManager(context, 3)
    itemAdapter = context?.let { ItemGridAdapter(it, layoutInflater) }!!
    recycler_search.adapter = itemAdapter
  }

  private fun initSearchView() {
    search_bar.addTextChangedListener(object :TextWatcher {
      override fun afterTextChanged(s: Editable?) {}

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        queryString = s.toString()
        when (queryType) {
          "Movie" -> {
            MoviesRepository.searchMovie(
              query = s.toString(),
              onSuccess = ::onItemsFetched,
              onError = ::onError
            )
          }

          "TV shows" -> {
            MoviesRepository.searchTVShow(
              query = s.toString(),
              onSuccess = ::onItemsFetched,
              onError = ::onError
            )
          }
        }
      }
    })
  }

  private fun onItemsFetched(items: List<Any>) {
    itemAdapter.updateItems(items)
  }

  private fun onError() {
    Log.e(TAG, "Errore")
  }
}