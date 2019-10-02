package com.example.hp.thejobmanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast



class WorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)


        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_jobs -> {
                    replaceFragment(WorkerAvailableJobragment())
                    Toast.makeText(this,"available",Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_accjobs -> {
                    Toast.makeText(this,"available",Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_rejjobs -> {
                    Toast.makeText(this,"available",Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    replaceFragment(WorkerProfileFragment())
                    Toast.makeText(this,"available",Toast.LENGTH_SHORT).show()
                    return@OnNavigationItemSelectedListener true
                }
            }

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
