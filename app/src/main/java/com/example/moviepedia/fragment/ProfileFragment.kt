package com.example.moviepedia.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.R
import com.example.moviepedia.activity.ProfileActivity
import com.example.moviepedia.activity.SettingsActivity
import com.example.moviepedia.activity.StatsActivity
import com.example.moviepedia.db.FirestoreUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.profile_stats.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

class ProfileFragment : Fragment() {
    private val TAG = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUsername()
        setUserStats()
        initTitleBarButtons()
        initToggleGroup()
    }

    private fun setUserStats() {
        val firestoreUtils = FirestoreUtils()
        firestoreUtils.getUserStats(object : FirestoreUtils.FirestoneCallback {
            override fun onCallback(list: MutableMap<String, Any>) {
                if (tw_place_value != null)         tw_place_value.text = list["rank"].toString()
                if (tw_watchlist_value != null)     tw_watchlist_value.text = list["watchlist"].toString()
                if (tw_movie_value != null)         tw_movie_value.text = list["movies"].toString()
                if (tw_series_value != null)        tw_series_value.text = list["tvshow"].toString()
            }
        })
    }

    private fun setUsername() {
        val firestoreUtils = FirestoreUtils()
        firestoreUtils.getUserData(object : FirestoreUtils.FirestoneCallback {
            override fun onCallback(list: MutableMap<String, Any>) {
                if (tw_profile_username != null)
                    tw_profile_username.text = list["username"].toString()
            }
        })
    }

    private fun initToggleGroup() {
        toggle_group_profile.setOnSelectListener { button: ThemedButton ->
           // TODO aggiungere cose
        }
    }

    private fun initTitleBarButtons() {

        btn_stats.setOnClickListener {
            val intent = Intent(context,
                StatsActivity::class.java)
            startActivity(intent)
        }

        btn_setting.setOnClickListener {
            val dialog = context?.let { it1 -> BottomSheetDialog(it1) }
            val view = layoutInflater.inflate(R.layout.setting_bottom_sheet, null,false)
            dialog!!.window!!.attributes!!.windowAnimations = R.style.DialogAnimation
            dialog.setContentView(view)
            dialog.show()

            dialog.findViewById<TextView>(R.id.textview_settings)!!.setOnClickListener {
                val intent = Intent(context,
                    SettingsActivity::class.java)
                startActivity(intent)
            }

            dialog.findViewById<TextView>(R.id.textview_profile)!!.setOnClickListener {
                val intent = Intent(context,
                    ProfileActivity::class.java)
                startActivity(intent)
            }

            dialog.findViewById<TextView>(R.id.textview_signout)!!.setOnClickListener {
                LoginActivity.signOut()
                dialog.dismiss()
                val intent = Intent(activity, LoginActivity::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}