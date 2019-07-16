package com.example.hp.thejobmanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.widget.Toast
import android.databinding.adapters.CompoundButtonBindingAdapter.setChecked



class SupervisorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supervisor)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        replaceFragment(SupervisorJobFragment())

        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_jobs -> {
                    replaceFragment(SupervisorJobFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_addjobs -> {
                    replaceFragment(AddNewJobFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    replaceFragment(SupervisorProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            replaceFragment(SupervisorJobFragment())
            return@OnNavigationItemSelectedListener true
        }
        )


    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment)
        fragmentTransaction.commit()
    }
}
