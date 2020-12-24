package com.example.moviepedia.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.profile_stats.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton

class ProfileFragment : Fragment() {
    private val TAG = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
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
        initTitleBarButtons()
        initToggleGroup()
    }

    private fun setUsername() {
        LoginActivity.getUserData(object : LoginActivity.UserValue.FirestoreCallback {
            override fun onCallback(list: MutableMap<String, Any>) {
                tw_profile_username.text = list["username"].toString()
            }
        })
        // TODO aggiungere cose
    }

    private fun initToggleGroup() {
        toggle_group_profile.setOnSelectListener { button: ThemedButton ->
           // TODO aggiungere cose
        }
    }

    private fun initTitleBarButtons() {

        btn_stats.setOnClickListener {
            // TODO inviare a nuova activity (Stats)
        }

        btn_setting.setOnClickListener {
            val dialog = context?.let { it1 -> BottomSheetDialog(it1) }
            val view = layoutInflater.inflate(R.layout.setting_bottom_sheet, null,false)
            dialog!!.window!!.attributes!!.windowAnimations = R.style.DialogAnimation
            dialog.setContentView(view)
            dialog.show()

            dialog.findViewById<TextView>(R.id.textview_settings)!!.setOnClickListener {
                Log.d(TAG, "Settings")
                // TODO inviare a nuova activity (Settings)
            }

            dialog.findViewById<TextView>(R.id.textview_profile)!!.setOnClickListener {
                Log.d(TAG, "Profile")
                // TODO inviare a nuova activity (Profile)
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