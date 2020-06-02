package com.yrc.pos.features.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.yrc.pos.R
import com.yrc.pos.core.Constants
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.services.SessionManagement
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User
import com.yrc.pos.core.views.YrcTextView
import com.yrc.pos.features.login.LoginActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivity : YrcBaseActivity(), NavigationView.OnNavigationItemSelectedListener {
//    private lateinit var drawerLayout: DrawerLayout
    private lateinit var textViewHeaderTitle: YrcTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.abs_layout)
        supportActionBar!!.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.header_background))
        textViewHeaderTitle = supportActionBar!!.customView.findViewById<YrcTextView>(R.id.textViewTitle)

        bottom_nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
        navigationDrawerView.setupWithNavController(findNavController(R.id.nav_host_fragment))

//        drawerLayout = findViewById(R.id.drawer_layout)
//        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment), drawerLayout)
        navigationDrawerView.setNavigationItemSelectedListener(this)

        setNavigationDrawerHeaderData()
    }

    private fun setNavigationDrawerHeaderData() {

        val headerView = navigationDrawerView.getHeaderView(0)
        val textViewUserName = headerView.findViewById(R.id.textView_userName) as YrcTextView
        val imageViewUserPhoto = headerView.findViewById(R.id.imageView_userPhoto) as ImageView

        if (User.getUserName()!!.isNotEmpty()) {
            textViewUserName.text = User.getUserName()
            textViewHeaderTitle.text = getString(R.string.welcome) + Constants.SPACE_STRING + User.getUserName()
        }

        val resId = resources.getIdentifier(User.getUserProfile()!!.avatar, "drawable", this.packageName)
        if (resId > 0) {
            imageViewUserPhoto.setImageResource(resId)
        } else {
            imageViewUserPhoto.setImageResource(R.drawable.female_avatar_1)
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(drawerLayout) || super.onSupportNavigateUp()
//    }

//    override fun onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        menuItem.isChecked = true
//        drawerLayout.closeDrawers()

        when (menuItem.itemId) {
            R.id.item_sign_out -> {
                var session: SessionManagement = SessionManagement(this)
                session.LogoutUser()
               // Session.clearSession()
                moveToLoginScreen()
                finish()
            }
        }
        return true
    }

    private fun moveToLoginScreen() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)
        finish()
    }
}