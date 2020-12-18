package com.example.moviepedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.iammert.library.readablebottombar.ReadableBottomBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val userId = intent.getStringExtra("user_id")

        manageBottomBar()
    }

    /**
     *  https://github.com/iammert/ReadableBottomBar
     */
    private fun manageBottomBar() {
    }
}