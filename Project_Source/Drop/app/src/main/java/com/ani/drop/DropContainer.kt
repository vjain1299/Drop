package com.ani.drop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_drop_container.*

class DropContainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_container)
    }

    override fun onStart() {
        super.onStart()
        val title = intent.extras?.get("title")
        val body = intent.extras?.get("body")
        TitleBox.text = title as String
        DropBox.text = body as String
    }
}
