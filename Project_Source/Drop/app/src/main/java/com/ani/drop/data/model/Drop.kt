package com.ani.drop.data.model


import com.ani.drop.DataAccess.mFirebaseAuth
import com.ani.drop.DropHolder
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser
import java.util.*

class Drop(own : FirebaseUser, sub : String, msg : String, prv: String, latLng: LatLng) {

    val timestamp : Calendar = Calendar.getInstance()
    val owner : FirebaseUser = own
    val subject : String = sub
    val message : String = msg
    val privacy : String = prv
    val position : LatLng = latLng

    fun isSharedWithPublic() : Boolean {
        return privacy != "friends"
    }
    fun dropToHolder() : DropHolder {
        return DropHolder("",position.latitude,position.longitude,message, timestamp.get(Calendar.DATE), subject)
    }

  /*  fun ownsDrop(user : FirebaseUser) : Boolean {
        return user == owner
    }
    override fun toString() : String {
        return (owner.displayName + "\n" + subject)
        }
  */
    /* fun encodeDrop() : String{

    }
    */
    companion object {
        fun dropHolderToDrop(dh : DropHolder) : Drop {
            return Drop(mFirebaseAuth!!.currentUser!! ,dh.TopicLabel!!, dh.MessageContent!!, "public", LatLng(dh.Latitude!!,dh.Longitude!!))
        }
    }

}