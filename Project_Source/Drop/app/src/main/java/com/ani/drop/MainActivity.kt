package com.ani.drop
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.ani.drop.DataAccess.RADIUS
import com.ani.drop.data.model.Drop
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var isMapSensitive = false
    private lateinit var mFirebaseFirestore : FirebaseFirestore
    private var dropList : List<Drop>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseFirestore = FirebaseFirestore.getInstance()
        dropList = getDropsInRadius(LatLng(0.0,0.0),mFirebaseFirestore)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        dropList?.forEach { drop ->
            addMarker(drop)
        }


        val fab: FloatingActionButton = findViewById(R.id.addPeg)
        fab.setOnClickListener { view ->
           Snackbar.make(view, if(isMapSensitive) "Cancelled" else "Select Location for Drop", Snackbar.LENGTH_LONG).show()
            isMapSensitive = !isMapSensitive
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navView.setNavigationItemSelectedListener(this)
    }
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.nav_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this, DropListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sent -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // val cleveland = LatLng(41.4993, -81.6944) // For fun (might come in handy later)
        val mapClicked : GoogleMap.OnMapClickListener = GoogleMap.OnMapClickListener { addDropToMap(it) }
        mMap.setOnMapClickListener(mapClicked)

    }
    private fun addDropToMap(position : LatLng) {
        if(isMapSensitive) {
            mMap.addMarker(MarkerOptions().position(position))
            isMapSensitive = false
            val intent = Intent(this, AddDropActivity::class.java)
            intent.putExtra("LatLng", position)
            startActivity(intent)
        }
    }
    private fun addMarker(drop : Drop) {
        mMap.addMarker(MarkerOptions().position(drop.position).title(drop.subject))
    }
    fun getDropsInRadius(position : LatLng, mFirebaseFirestore : FirebaseFirestore) : List<Drop>? {
        val drops = mFirebaseFirestore.collection("dropsCollection")
        var dropList : List<Drop>? = null
        val filteredDrops = drops
            .whereLessThanOrEqualTo("Latitude",position.latitude + RADIUS)
            //.whereLessThanOrEqualTo("Longitude",position.longitude + RADIUS)
            .whereGreaterThanOrEqualTo("Latitude", position.latitude - RADIUS)
            //.whereGreaterThanOrEqualTo("Longitude", position.longitude - RADIUS)
        filteredDrops.addSnapshotListener { snapshot, e ->
            if(e != null) {
                Log.w("GetDrops","Listen Failed.", e)
                return@addSnapshotListener
            }
            if(snapshot != null && snapshot.first().exists()) {
                Log.d("GetDrops", "Current Data: ${snapshot.first().data}")
                dropList = snapshot.toObjects(DropHolder::class.java).map{ dh ->
                    Drop.dropHolderToDrop(dh)
                }
            }
            else {
                Log.d("GetDrops", "Current data: null")
            }
        }
        DataAccess.DropList = dropList
        return dropList
    }


}
