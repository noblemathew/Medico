package com.simplyany.medico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgetPassword : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var button: Button
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        email = findViewById(R.id.et_forgetemail)
        button = findViewById(R.id.btn_sendverification)
        firebaseAuth = FirebaseAuth.getInstance()
        button.setOnClickListener() {
            val emailf = email.text.toString()
            if (emailf.isEmpty()) {
                Toast.makeText(this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth!!.sendPasswordResetEmail(emailf).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this, "Verification mail successfully sent", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }
    }
}