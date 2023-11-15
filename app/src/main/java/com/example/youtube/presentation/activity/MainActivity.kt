package com.example.youtube.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.youtube.R

class MainActivity : AppCompatActivity() {

    /*private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*    val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/
}