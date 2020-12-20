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
import kotlinx.android.synthetic.main.setting_bottom_sheet.*


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        initTitleBarButtons()
    }

    private fun initTitleBarButtons() {
        btn_setting.setOnClickListener {
            val dialog = context?.let { it1 -> BottomSheetDialog(it1) }
            val view = layoutInflater.inflate(R.layout.setting_bottom_sheet, null,false)
            dialog!!.window!!.attributes!!.windowAnimations = R.style.DialogAnimation
            dialog.setContentView(view)
            dialog.show()

            dialog.findViewById<TextView>(R.id.textview_settings)!!.setOnClickListener {
                Log.d(TAG, "Settings")
            }

            dialog.findViewById<TextView>(R.id.textview_profile)!!.setOnClickListener {
                Log.d(TAG, "Profile")
            }

            dialog.findViewById<TextView>(R.id.textview_signout)!!.setOnClickListener {
                LoginActivity.signOut()
                val intent = Intent(activity, LoginActivity::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}