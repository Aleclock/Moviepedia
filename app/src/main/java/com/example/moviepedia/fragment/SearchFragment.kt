package com.example.moviepedia.fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.moviepedia.R
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_search.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

class SearchFragment : Fragment() {
    val TAG = "SearchFragment"

    lateinit var queryString : String
    lateinit var queryType : String

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

        initToggleGroup()
        initSearchView()
    }

    private fun initToggleGroup() {
        val allButtons = toggle_group_search.buttons
        toggle_group_search.selectButton(allButtons[0].id)
        queryType = allButtons[0].text

        toggle_group_search.setOnSelectListener { button: ThemedButton ->
            queryType = button.text
        }
    }

    private fun initSearchView() {
        search_bar.addTextChangedListener(object :TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO make research
            }

        })
    }

}