package com.example.soundboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPref : SharedPreferences
    private lateinit var fragments : ArrayList<Fragment>
    private var lastFragmentIndex : Int = 0
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)


        val homeFragment = HomeFragment()
        val discoverFragment = DiscoverFragment()
        val profileFragment = ProfileFragment()
        val settingsFragment = SettingsFragment()
        val loginFragment = LoginFragment()
        val registerFragment = RegisterFragment()

        fragments = ArrayList<Fragment>()
        fragments.add(homeFragment)
        fragments.add(discoverFragment)
        fragments.add(profileFragment)
        fragments.add(settingsFragment)
        fragments.add(loginFragment)
        fragments.add(registerFragment)

        if (savedInstanceState != null) {
            lastFragmentIndex = savedInstanceState.getInt("last_fragment_key")
        }
        setCurrentFragment(lastFragmentIndex)    //start on where you left off

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    setCurrentFragment(0)
                    true
                }
                R.id.navigation_discover -> {
                    setCurrentFragment(1)
                    true
                }
                R.id.navigation_profile -> {
                    setCurrentFragment(2)
                    true
                }
                R.id.navigation_settings -> {
                    setCurrentFragment(5)
                    true
                }
                else -> false
            }
        }
    }

    public fun registerToLogin(){
        setCurrentFragment(6)
    }

    private fun setCurrentFragment(index : Int) {
        lastFragmentIndex = index
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragments[index])
            addToBackStack(null)
            commit()
        }
    }

    /* overrides back press to have instagram-esqe functionality
     * back on homescreen exits, back on anything else goes to home
     * TODO: figure out login/register back behavior
     */
    override fun onBackPressed() {
        if( navView.selectedItemId == R.id.navigation_home ){
            super.onBackPressed()
            finish()
        }else{
            navView.selectedItemId = R.id.navigation_home
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("last_fragment_key", lastFragmentIndex)
        super.onSaveInstanceState(outState)
    }

}
