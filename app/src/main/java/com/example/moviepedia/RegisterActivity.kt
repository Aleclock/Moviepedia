 package com.example.moviepedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*


 class RegisterActivity : AppCompatActivity() {

     private val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        login_title.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

        btn_register.setOnClickListener{
            createNewAccount()
        }
    }

     private fun createNewAccount() {
         when {
             TextUtils.isEmpty(et_email.text.toString().trim{it <= ' '}) -> {
                 println("Empty email")
             }
             TextUtils.isEmpty(et_username.text.toString().trim{it <= ' '}) -> {
                 println("Empty username")
             }
             TextUtils.isEmpty(et_password.text.toString().trim{it <= ' '}) -> {
                 println("Empty password")
             }

             else -> {
                 val email: String = et_email.text.toString().trim{ it <= ' '}
                 val username: String = et_username.text.toString().trim{ it <= ' '}
                 val password: String = et_password.text.toString().trim{ it <= ' '}

                 FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                     .addOnCompleteListener { task ->

                         // if registration is successfully done
                         if (task.isSuccessful) {
                             val userID: FirebaseUser = task.result!!.user!!

                             //saveUserToDB(userID, email, username)

                             Log.d(TAG, "createUserWithEmail:success")

                             val intent = Intent(this, MainActivity::class.java)
                             intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                             intent.putExtra("user_id", userID.uid)
                             intent.putExtra("email_id", email)
                             startActivity(intent)
                             finish()

                         } else {
                             Log.e(TAG, "createUserWithEmail:failure", task.exception)
                         }
                     }
             }
         }
     }

     /*private fun saveUserToDB(userID: String, email: String, username: String) {
         val currentUserDb = mDatabaseReference!!.child(userId)

     }*/
}