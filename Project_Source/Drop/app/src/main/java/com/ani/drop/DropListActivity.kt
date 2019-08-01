package com.ani.drop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ani.drop.data.model.Drop

class DropListActivity : AppCompatActivity(), DropFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_list2)
    }

    override fun onListFragmentInteraction(item: Drop?) {
        var dropIntent = Intent(this, DropContainer::class.java)
        dropIntent.putExtra("title", item?.subject)
        dropIntent.putExtra("body", item?.message)
        startActivity(dropIntent)
    }
}
