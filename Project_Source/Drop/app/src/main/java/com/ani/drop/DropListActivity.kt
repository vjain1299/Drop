package com.ani.drop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ani.drop.data.model.Drop

class DropListActivity : AppCompatActivity(), DropFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_list2)
    }

    override fun onListFragmentInteraction(item: Drop?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
