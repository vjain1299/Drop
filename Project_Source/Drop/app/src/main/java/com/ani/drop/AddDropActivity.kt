package com.ani.drop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ani.drop.data.model.Drop
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_add_drop.*
import kotlinx.android.synthetic.main.content_add_drop.*

class AddDropActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drop)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val spot = intent.extras?.get("LatLng")

            val title : String = topicInput.text.toString()

            val body : String = messageInput.text.toString()

            val userInfo : FirebaseUser? = FirebaseAuth.getInstance().currentUser

            val privacy = if(toggleButton.isChecked) "friends" else "everyone"

            val newDrop = Drop(userInfo as FirebaseUser , title, body, privacy, spot as LatLng)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
