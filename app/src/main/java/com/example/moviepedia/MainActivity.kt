package com.example.moviepedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.moviepedia.fragment.DiscoverFragment
import com.example.moviepedia.fragment.MoviesFragment
import com.example.moviepedia.fragment.ProfileFragment
import com.example.moviepedia.fragment.SearchFragment
import com.iammert.library.readablebottombar.ReadableBottomBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var moviesFragment = MoviesFragment()
        setFragment(moviesFragment)
        manageBottomBar()
    }



    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .commit()
    }

    /**
     *  https://github.com/iammert/ReadableBottomBar
     */
    private fun manageBottomBar() {
        // TODO capire perchè ogni tanto tuona
        readable_bottom_bar.setOnItemSelectListener( object :ReadableBottomBar.ItemSelectListener{
            override fun onItemSelected(index: Int) {
                when(index) {
                    0 -> {
                        var moviesFragment = MoviesFragment()
                        setFragment(moviesFragment)
                    }
                    1 -> {
                        var discoverFragment = DiscoverFragment()
                        setFragment(discoverFragment)
                    }
                    2 -> {
                        var searchFragment = SearchFragment()
                        setFragment(searchFragment)
                    }
                    3 -> {
                        var profileFragment = ProfileFragment()
                        setFragment(profileFragment)
                    }

                    }
                }
            }
        )
    }
}