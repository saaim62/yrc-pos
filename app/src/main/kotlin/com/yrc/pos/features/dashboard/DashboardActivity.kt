package com.yrc.pos.features.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
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
import com.yrc.pos.core.YrcFrameActivity
import com.yrc.pos.core.Tags
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User
import com.yrc.pos.core.views.YrcTextView
import com.yrc.pos.features.login.LoginActivity
import com.yrc.pos.features.profile.ProfileFragment
import com.yrc.pos.features.support.SupportDialog
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivity : YrcBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
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

        drawerLayout = findViewById(R.id.drawer_layout)
        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment), drawerLayout)
        navigationDrawerView.setNavigationItemSelectedListener(this)

        setNavigationDrawerHeaderData()
    }

    fun setNavigationDrawerHeaderData() {

        val headerView = navigationDrawerView.getHeaderView(0)
        val textViewUserName = headerView.findViewById(R.id.textView_userName) as YrcTextView
        val imageViewUserPhoto = headerView.findViewById(R.id.imageView_userPhoto) as ImageView

        if (User.getUserName()!!.isNotEmpty()) {
            textViewUserName.text = User.getUserName()
            textViewHeaderTitle.text = getString(R.string.welcome) + Constants.SPACE_STRING + User.getUserName()
        }

        var resId = resources.getIdentifier(User.getUserProfile()!!.avatar, "drawable", this.packageName)
        if (resId > 0) {
            imageViewUserPhoto.setImageResource(resId)
        } else {
            imageViewUserPhoto.setImageResource(R.drawable.ic_action_name)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(drawerLayout) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        menuItem.isChecked = true
        drawerLayout.closeDrawers()

        when (menuItem.itemId) {
//            R.id.item_manage_profile -> {
//                moveToProfileScreen()
//            }
            R.id.item_sign_out -> {
                Session.clearSession()
                moveToLoginScreen()
            }
//            R.id.item_support -> {
//                showSupportDialog()
//            }
//            R.id.item_faqs -> {
//                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
//            }
//            R.id.item_rate_us -> {
//                Toast.makeText(this, "Feature coming soon..", Toast.LENGTH_SHORT).show()
//            }
        }
        return true
    }

    private fun moveToProfileScreen() {
        val bundle = Bundle()
        bundle.putString(YrcFrameActivity.FRAGMENT_NAME_STRING, ProfileFragment::class.java.name)
        bundle.putString(YrcFrameActivity.ACTIVITY_TITLE, resources.getString(R.string.edit_profile))
        bundle.putBoolean(YrcFrameActivity.INFLATE_OPTIONS_MENU, true)
        startActivity(Intent(applicationContext, YrcFrameActivity::class.java).putExtras(bundle))
    }

    private fun moveToLoginScreen() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(loginIntent)
        finish()
    }

    private fun showSupportDialog() {
        SupportDialog().show(supportFragmentManager.beginTransaction(), Tags.SupportDialogFragment)
    }
}