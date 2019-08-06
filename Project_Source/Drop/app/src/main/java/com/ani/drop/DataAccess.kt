package com.ani.drop

import com.ani.drop.data.model.Drop
import com.google.firebase.auth.FirebaseAuth

object DataAccess {
    var mFirebaseAuth : FirebaseAuth? = null
    val RADIUS = 10000
    var DropList : List<Drop>? = null
}