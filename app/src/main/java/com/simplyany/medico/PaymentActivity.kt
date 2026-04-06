package com.simplyany.medico

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast

class PaymentActivity : AppCompatActivity() {
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var editText4: EditText
    private lateinit var button: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        editText1=findViewById(R.id.cnumber)
        editText2=findViewById(R.id.cname)
        editText3=findViewById(R.id.cvv)
        editText4=findViewById(R.id.date)
        button=findViewById(R.id.submit)
        button.setOnClickListener{
            val s1=editText1.text
            val s2=editText2.text
            val s3=editText3.text
            val s4=editText4.text
            if(s1.isEmpty() && s2.isEmpty() && s3.isEmpty() && s4.isEmpty()){
                Toast.makeText(this,"Empty fills are not allowed",Toast.LENGTH_SHORT).show()

            }
            else{
                val intent = Intent(applicationContext, DoneActivity::class.java)
                startActivity(intent);
                finish()
            }

        }
    }
}