package com.ani.drop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ani.drop.DataAccess.mFirebaseAuth
import com.ani.drop.data.model.Drop
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_drop.*
import kotlinx.android.synthetic.main.content_add_drop.*

class AddDropActivity : AppCompatActivity() {
    private lateinit var mFirebaseFirestore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drop)
        setSupportActionBar(toolbar)
        mFirebaseFirestore = FirebaseFirestore.getInstance()

        fab.setOnClickListener { view->
            val spot = intent.extras?.get("LatLng")

            val title : String = topicInput.text.toString()

            val body : String = messageInput.text.toString()

            val userInfo : FirebaseUser? = mFirebaseAuth?.currentUser
            Log.w("Firebase Add", "User" + mFirebaseAuth?.currentUser?.uid)

            val privacy = if(toggleButton.isChecked) "friends" else "everyone"

            val newDrop = Drop(mFirebaseAuth?.currentUser!!, title, body, privacy, spot as LatLng)

            mFirebaseFirestore.collection("dropsCollection")?.add(newDrop.dropToHolder())?.addOnFailureListener {
                Snackbar.make(view ,"Drop Failed to Post", Snackbar.LENGTH_LONG)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
