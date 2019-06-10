package com.develop.grizzzly.pediatry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.develop.grizzzly.pediatry.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {


    private var registered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        if (registered) {
            val intent = Intent(baseContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            val navController = nav_host_fragment.findNavController()
            navController.navigate(R.id.action_start_to_login)
        }
    }


    override fun onStart() {
        supportActionBar?.hide()
        super.onStart()
    }
}
