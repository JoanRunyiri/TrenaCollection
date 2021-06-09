package com.kanyideveloper.letsgoshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            //start the signup activity
            startActivity(intent)
        }
        signin_btn.setOnClickListener {
            if (loginEmail.text.trim().toString().isNotEmpty() || loginpassword.text.trim()
                    .toString().isNotEmpty()
            ) {
                signInUser(loginEmail.text.trim().toString(), loginpassword.text.trim().toString())

            } else {
                Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun signInUser(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener (this){ task ->

                if (task.isSuccessful){
                    val intent = Intent(this,MainActivity::class.java)

                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this,"Invalid Email and Password" +task.exception, Toast.LENGTH_LONG).show()
                }
            }

    }
}



