package com.omdb.movie.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.omdb.movie.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        // TODO: Track screen destination
    }

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host)
        navController?.setGraph(R.navigation.home_nav, null)
    }

    override fun onResume() {
        super.onResume()
        navController?.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController?.removeOnDestinationChangedListener(listener)
    }
}
