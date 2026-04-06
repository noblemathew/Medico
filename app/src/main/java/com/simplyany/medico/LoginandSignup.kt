package com.simplyany.medico

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class LoginandSignup : AppCompatActivity() {
    lateinit var viewPager:ViewPager
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var p:AuthenticationPagerAdapter
    lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginand_signup)
        viewPager=findViewById(R.id.viewPager)
        dbRef= FirebaseDatabase.getInstance().getReference("Users")
        p=AuthenticationPagerAdapter(supportFragmentManager)
        p.addFragmet(Login())
        p.addFragmet(Signup())
        viewPager.adapter = p
        firebaseAuth = FirebaseAuth.getInstance()
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        val user = Firebase.auth.currentUser
        if (user != null) {
            val nam2=user.email!!.replaceAfter("@","")
            if (user.isEmailVerified) {
               dbRef.child(nam2).get().addOnSuccessListener {
                    if (it.exists()){
                        val name=it.value.toString()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("id","Hello $name !")
                        startActivity(intent)
                        finish()
                    }
                   else{
                        Toast.makeText(
                            this,
                            "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener{

                }

            }
        }
        onBackPressedDispatcher.addCallback(this) {
            val dialog = AlertDialog.Builder(this@LoginandSignup)
            dialog.setTitle("Exit")
            dialog.setMessage("Do you want to exit?")
            dialog.setPositiveButton("Yes") { _, _ ->
                finish()
            }
            dialog.setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            dialog.show()
        }

    }
    class AuthenticationPagerAdapter(fm: FragmentManager?) :
        FragmentPagerAdapter(fm!!) {
        private val fragmentList: ArrayList<Fragment> = ArrayList<Fragment>()
        override fun getItem(i: Int): Fragment {
            return fragmentList[i]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragmet(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }


}
