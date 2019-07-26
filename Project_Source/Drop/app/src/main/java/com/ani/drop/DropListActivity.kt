package com.ani.drop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ani.drop.dummy.DummyContent

class DropListActivity : AppCompatActivity(), DropFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_list2)
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
