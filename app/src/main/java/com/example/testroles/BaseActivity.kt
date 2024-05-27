package com.example.testroles

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BaseActivity : AppCompatActivity() {
    private var et: EditText? = null
    private var tv: TextView? = null
    private var bt: Button? = null

    private var mDatabase: DatabaseReference? = null
    var user: FirebaseUser? = null
    var user1: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        et = findViewById(R.id.et)
        tv = findViewById(R.id.tv)
        bt = findViewById(R.id.bt)
        user = FirebaseAuth.getInstance().currentUser
        mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(user!!.uid)

        val postListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user1 = dataSnapshot.getValue(User::class.java)
                tv!!.text = user1!!.getSmth().toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabase!!.addValueEventListener(postListener)

        bt?.setOnClickListener(View.OnClickListener {
            if (user1!!.getUsername() == "admin") {
                val user2 = User(
                    user1!!.getUid()!!,
                    user1!!.getUsername()!!,
                    user1!!.getEmail()!!,
                    et!!.getText().toString()
                )
                mDatabase!!.setValue(user2)
            } else {
                Toast.makeText(applicationContext, "Нет прав", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
