package com.example.moviepedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_login.signup_title

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = mAuth.uid

        if (user != null)
            updateUI(uid!!)
        else
            signup_title.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }

        btn_login.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                println("Empty email")
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                println("Empty password")
            }
            else -> {
                val email: String = et_email.text.toString().trim{ it <= ' '}
                val password: String = et_password.text.toString().trim{ it <= ' '}

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmailAndPassword:success")
                            updateUI(FirebaseAuth.getInstance().currentUser!!.uid)

                        } else {
                            Log.e(TAG, "signInWithEmailAndPassword:failure", task.exception)
                        }
                    }
            }
        }

    }

    private fun updateUI(userID: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id", userID)
        startActivity(intent)
        finish()
    }

    companion object UserValue {
        var mAuth = FirebaseAuth.getInstance()

        fun signOut() {
            mAuth?.signOut()
        }

        fun getEmail() : String {
            return mAuth!!.currentUser!!.email!!
        }
    }
}