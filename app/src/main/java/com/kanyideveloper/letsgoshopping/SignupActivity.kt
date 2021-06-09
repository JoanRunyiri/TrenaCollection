package com.kanyideveloper.letsgoshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signin_btn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            //start the signup activity
            startActivity(intent)
        }



//        fun OpenSignInPage(view: View) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)}





        auth = FirebaseAuth.getInstance()

        signup_btn.setOnClickListener {
            if (email.text.trim().toString().isNotEmpty()||password.text.trim().toString().isNotEmpty()){
                createUser(email.text.trim().toString(), password.text.trim().toString())
                Toast.makeText(this,"Input provided", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Input is required", Toast.LENGTH_LONG).show()
            }



        }
    }
    fun createUser(email:String,password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task->

                if(task.isSuccessful){
                    val intent = Intent(this,MainActivity::class.java);
                    startActivity(intent)
                    //uploadImageToFirebaseStorage()

                }else{
                    Toast.makeText(this,"Failed" +task.exception, Toast.LENGTH_LONG).show()
                }
            }


    }






}
