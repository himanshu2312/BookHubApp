package com.himanshu.bookhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
    private lateinit var coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout
    private lateinit var frameLayout: FrameLayout
    private lateinit var navigationView: com.google.android.material.navigation.NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private var previousMenuItem: MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)
        setUpToolbar()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.setDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        openDashboard()

        navigationView.setNavigationItemSelectedListener {
            if (previousMenuItem!=null) previousMenuItem!!.isChecked=false
            it.isChecked=true
            when (it.itemId) {
                R.id.dashboard -> openDashboard()
                R.id.profile -> openProfile()
                R.id.aboutApp -> openAboutApp()
                R.id.favorite -> openFavorite()
            }
            previousMenuItem=it
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dashboard"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openDashboard(){
        val fragment=DashboardFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
        supportActionBar?.title="Dashboard"
        drawerLayout.closeDrawers()
        navigationView.setCheckedItem(R.id.dashboard)
    }

    private fun openProfile(){
        val fragment=ProfileFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
        supportActionBar?.title="Profile"
        drawerLayout.closeDrawers()
    }

    private fun openAboutApp(){
        val fragment=AboutAppFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
        supportActionBar?.title="About App"
        drawerLayout.closeDrawers()
    }

    private fun openFavorite(){
        val fragment=FavoriteFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
        supportActionBar?.title="Favorite"
        drawerLayout.closeDrawers()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        when(supportFragmentManager.findFragmentById(R.id.frameLayout)){
            !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }

    }

}