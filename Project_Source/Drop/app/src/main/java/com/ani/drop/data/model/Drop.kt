package com.ani.drop.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser

class Drop(own : FirebaseUser, sub : String, msg : String, prv: String, latLng: LatLng) {

    private val owner : FirebaseUser = own
    private val subject : String = sub
    private val message : String = msg
    private val privacy : String = prv
    private val position : LatLng = latLng

    fun isSharedWithPublic() : Boolean {
        return privacy == "friends"
    }

    fun ownsDrop(user : FirebaseUser) : Boolean {
        return user == owner
    }
    /* fun encodeDrop() : String{

    }
    companion object {
        fun decodeDrop(): Drop {

        }
    }
    */
}