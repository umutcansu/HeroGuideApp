package com.thell.heroguideapp.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.thell.heroguideapp.R
import com.thell.heroguideapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private  var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(navController!!.graph)
        NavigationUI.setupActionBarWithNavController(this, navController!!, appBarConfiguration)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return true
    }
}