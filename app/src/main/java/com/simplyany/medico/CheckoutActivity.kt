package com.simplyany.medico

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso


class CheckoutActivity : AppCompatActivity() {
    private lateinit var medn: TextView
    private lateinit var medp:TextView
    private lateinit var medi:ImageView
    private lateinit var price:TextView
    private lateinit var same:TextView
    private lateinit var button: Button
    private lateinit var editText:EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var cod:RadioButton
    private lateinit var card: RadioButton
    lateinit var dbRef: DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        dbRef= FirebaseDatabase.getInstance().getReference("History")
        medn=findViewById(R.id.medname)
        medp=findViewById(R.id.medprice)
        medi=findViewById(R.id.medimg2)
        price=findViewById(R.id.priced)
        same=findViewById(R.id.sameasprice)
        button=findViewById(R.id.btn_done)
        editText=findViewById(R.id.et_address)
        radioGroup=findViewById(R.id.radio)
        cod=findViewById(R.id.cod)
        card=findViewById(R.id.card)
        val intent = intent
        val medname = intent.getStringExtra("Med_name")
        val medimage = intent.getStringExtra("Med_Image")
        val medprice = intent.getStringExtra("Med_price")
        medn.text=medname
        medp.text=medprice
        price.text=medprice
        same.text=medprice
        Picasso.get().load(medimage).into(medi)
        button.setOnClickListener{
            val s=editText.text
            if(s.isEmpty()){
                Toast.makeText(this,"Enter your address",Toast.LENGTH_SHORT).show()
            }
            else{
                if (radioGroup.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(this,"Select your mode of payment",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    if(cod.isChecked){
                        val user = Firebase.auth.currentUser
                        if(user!=null){
                            val username=user.email!!.replaceAfter("@","")
                            val db=dbRef.push().key!!
                            dbRef.child(username).child(medname.toString()).child("name").setValue(medname).addOnCompleteListener {

                            }.addOnFailureListener{
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                            dbRef.child(username).child(medname.toString()).child("price").setValue(medprice).addOnCompleteListener {

                            }.addOnFailureListener{
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                            dbRef.child(username).child(medname.toString()).child("image").setValue(medimage).addOnCompleteListener {

                            }.addOnFailureListener{
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }

                        }
                        val intent = Intent(applicationContext, DoneActivity::class.java)
                        startActivity(intent);
                        finish()
                    }
                    if(card.isChecked){
                        val user = Firebase.auth.currentUser
                        if(user!=null){
                            val username=user.email!!.replaceAfter("@","")
                            val db=dbRef.push().key!!
                            dbRef.child(username).child(medname.toString()).child("name").setValue(medname).addOnCompleteListener {

                            }.addOnFailureListener{
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                            dbRef.child(username).child(medname.toString()).child("price").setValue(medprice).addOnCompleteListener {

                            }.addOnFailureListener{
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                            dbRef.child(username).child(medname.toString()).child("image").setValue(medimage).addOnCompleteListener {

                            }.addOnFailureListener{
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }

                        }
                        val intent = Intent(applicationContext, PaymentActivity::class.java)
                        startActivity(intent);
                        finish()
                    }


                }


            }
        }

    }


}