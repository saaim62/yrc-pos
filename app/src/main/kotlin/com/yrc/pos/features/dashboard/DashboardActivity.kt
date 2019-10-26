package com.yrc.pos.features.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.yrc.pos.R
import com.yrc.pos.core.Constants
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.YrcFrameActivity
import com.yrc.pos.core.Tags
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
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

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.abs_layout)
        supportActionBar!!.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.header_background))
        textViewHeaderTitle = supportActionBar!!.customView.findViewById(R.id.textViewTitle)

        setNavigationDrawerHeaderData()

        setAdultButtonListener()
        setOver65ButtonListener()
        set1822ButtonListener()
        setRacegoerButtonListener()

        disposable = RxBus.listen(RxEvent.doThis::class.java).subscribe {
            when {
                it.buttonName == "onCrossButtonClicked" -> {
                    countAdultTickets = 0
                    countOver65Tickets = 0
                    count1822Tickets = 0
                    countRacegoerTickets = 0
                }
                it.buttonName == "onCashButtonClicked" -> Toast.makeText(this, "Printing tickets...", Toast.LENGTH_SHORT).show()
                it.buttonName == "onMultiplyButtonClicked" -> {
                    Toast.makeText(this, "Multiplying", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposable.isDisposed) disposable.dispose()
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
            imageViewUserPhoto.setImageResource(R.drawable.ic_action_name)
        }
    }

    private fun setAdultButtonListener() {
        button_Adult.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            countAdultTickets += 1
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            countOver65Tickets
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            count1822Tickets
            intent.putExtra(TICKET_1822, count1822Tickets)
            countRacegoerTickets
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    private fun setOver65ButtonListener() {
        button_Over65.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            countAdultTickets
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            countOver65Tickets += 1
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            count1822Tickets
            intent.putExtra(TICKET_1822, count1822Tickets)
            countRacegoerTickets
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    private fun set1822ButtonListener() {
        button_1822.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            countAdultTickets
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            countOver65Tickets
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            count1822Tickets += 1
            intent.putExtra(TICKET_1822, count1822Tickets)
            countRacegoerTickets
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    private fun setRacegoerButtonListener() {
        button_racegoer.setOnClickListener {
            val intent = Intent(this, CustomerSalesAddTicketQuantityActivity::class.java)
            countAdultTickets
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            countOver65Tickets
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            count1822Tickets
            intent.putExtra(TICKET_1822, count1822Tickets)
            countRacegoerTickets += 1
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
    }

    fun doThis() {
        countAdultTickets = 0
        countOver65Tickets = 0
        count1822Tickets = 0
        countRacegoerTickets = 0
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

    companion object {
        @JvmStatic val TICKET_ADULTS = "ticket_adults"
        @JvmStatic val TICKET_OVER65 = "ticket_over65"
        @JvmStatic val TICKET_1822 = "ticket_1822"
        @JvmStatic val TICKET_RACEGOER = "ticket_racegoer"
    }

}