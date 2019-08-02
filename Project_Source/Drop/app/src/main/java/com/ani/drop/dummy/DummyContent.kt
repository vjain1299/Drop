package com.ani.drop.dummy

import com.ani.drop.data.model.Drop
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val DROPS: MutableList<Drop> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Drop> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: Drop) {
        DROPS.add(item)
        var df : SimpleDateFormat = SimpleDateFormat("HH:mm:ss")
        ITEM_MAP.put(df.format(item.timestamp.time), item)
    }

    private fun createDummyItem(position: Int): Drop {
        return Drop("Subject" + position.toString(), "Hello, this drop content serves as a sample for the view.","friends", LatLng(-1.0,-1.0))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}
