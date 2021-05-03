package com.kristiania.exam2021

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed

class MainActivity : AppCompatActivity() {
    //Using a boolean to not show the splash screen everytime we enter the application
    var hasSeenSplashScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //This is the first screen, so we want to hide the top navigation bar describing our app
        supportActionBar?.hide()

        //We use the handler class to add a 3second delay to the MainActivity class
        if(!hasSeenSplashScreen){
            Handler().postDelayed({
                //We send an Intent letting our app know that after 3 seconds, we want to switch to the home screen (main screen for our app)
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
                hasSeenSplashScreen = true
            }, 3000)
        } else {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}