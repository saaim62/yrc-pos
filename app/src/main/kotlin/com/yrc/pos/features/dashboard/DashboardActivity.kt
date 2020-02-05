package com.yrc.pos.features.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.yrc.pos.R
import com.yrc.pos.core.*
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User
import com.yrc.pos.core.views.YrcTextView
import com.yrc.pos.features.login.LoginActivity
import com.yrc.pos.features.profile.ProfileFragment
import com.yrc.pos.features.support.SupportDialog
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_main.*

class DashboardActivity : YrcBaseActivity() {

    private var countAdultTickets = 0
    private var countOver65Tickets = 0
    private var count1822Tickets = 0
    private var countRacegoerTickets = 0

    private lateinit var textViewHeaderTitle: YrcTextView

    private lateinit var disposableClearAllTickets: Disposable
    private lateinit var disposableMultiplyTicket: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.abs_layout)
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.header_background
            )
        )
        textViewHeaderTitle = supportActionBar!!.customView.findViewById(R.id.textViewTitle)

        setNavigationDrawerHeaderData()

        setAdultButtonListener()
        setOver65ButtonListener()
        set1822ButtonListener()
        setRacegoerButtonListener()
        setTotalButtonListener()

        disposableClearAllTickets = RxBus.listen(RxEvent.buttonFunction::class.java).subscribe {
            countAdultTickets = 0
            countOver65Tickets = 0
            count1822Tickets = 0
            countRacegoerTickets = 0
            Toast.makeText(this, "Cleared all selections and reset", Toast.LENGTH_SHORT).show()
        }
        disposableMultiplyTicket = RxBus.listen(RxEvent.setTicketCount::class.java).subscribe {
            when {
                it.ticketName == TICKET_ADULTS -> {
                    countAdultTickets = it.count
                }
                it.ticketName == TICKET_OVER65 -> {
                    countOver65Tickets = it.count
                }
                it.ticketName == TICKET_1822 -> {
                    count1822Tickets = it.count
                }
                it.ticketName == TICKET_RACEGOER -> {
                    countRacegoerTickets = it.count
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        button_total.text = countAdultTickets.plus(countOver65Tickets).plus(count1822Tickets)
            .plus(countRacegoerTickets).toString().plus(" ")
            .plus(
                "x Ticket £".plus(
                        countAdultTickets.times(Prices.PRICE_ADULT)
                            .plus(countOver65Tickets.times(Prices.PRICE_OVER65))
                            .plus(count1822Tickets.times(Prices.PRICE_1822))
                            .plus(countRacegoerTickets.times(Prices.PRICE_RACEGOER))
                    )
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposableClearAllTickets.isDisposed) disposableClearAllTickets.dispose()
        if (!disposableMultiplyTicket.isDisposed) disposableMultiplyTicket.dispose()
    }

    private fun setNavigationDrawerHeaderData() {

        val headerView = navigationDrawerView.getHeaderView(0)
        val textViewUserName = headerView.findViewById(R.id.textView_userName) as YrcTextView
        val imageViewUserPhoto = headerView.findViewById(R.id.imageView_userPhoto) as ImageView

        if (User.getUserName()!!.isNotEmpty()) {
            textViewUserName.text = User.getUserName()
            textViewHeaderTitle.text =
                getString(R.string.welcome) + Constants.SPACE_STRING + User.getUserName()
        }

        val resId =
            resources.getIdentifier(User.getUserProfile()!!.avatar, "drawable", this.packageName)
        if (resId > 0) {
            imageViewUserPhoto.setImageResource(resId)
        } else {
            imageViewUserPhoto.setImageResource(R.drawable.ic_action_name)
        }
    }

    private fun setAdultButtonListener() {
        button_Adult.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            countAdultTickets += 1
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            intent.putExtra(TICKET_1822, count1822Tickets)
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    private fun setOver65ButtonListener() {
        button_Over65.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            countOver65Tickets += 1
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            intent.putExtra(TICKET_1822, count1822Tickets)
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    private fun set1822ButtonListener() {
        button_1822.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            count1822Tickets += 1
            intent.putExtra(TICKET_1822, count1822Tickets)
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    private fun setRacegoerButtonListener() {
        button_racegoer.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            intent.putExtra(TICKET_1822, count1822Tickets)
            countRacegoerTickets += 1
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    private fun setTotalButtonListener() {
        button_total.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            intent.putExtra(TICKET_1822, count1822Tickets)
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    private fun moveToProfileScreen() {
        val bundle = Bundle()
        bundle.putString(YrcFrameActivity.FRAGMENT_NAME_STRING, ProfileFragment::class.java.name)
        bundle.putString(
            YrcFrameActivity.ACTIVITY_TITLE,
            resources.getString(R.string.edit_profile)
        )
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.item_sign_out) {
            Session.clearSession()
            moveToLoginScreen()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        val TICKET_ADULTS = "ticket_adults"
        @JvmStatic
        val TICKET_OVER65 = "ticket_over65"
        @JvmStatic
        val TICKET_1822 = "ticket_1822"
        @JvmStatic
        val TICKET_RACEGOER = "ticket_racegoer"
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Not allowed", Toast.LENGTH_SHORT).show()
    }
}