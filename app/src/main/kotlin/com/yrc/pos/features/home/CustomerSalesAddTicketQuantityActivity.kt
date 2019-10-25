package com.yrc.pos.features.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.listeners.OnAvatarClickListener
import com.yrc.pos.core.listeners.OnCrossButtonClickListener
import com.yrc.pos.core.views.AvatarSelectionComponent
import kotlinx.android.synthetic.main.activity_customer_sales_add_ticket_quantity.*
import kotlinx.android.synthetic.main.fragment_profile.*

class CustomerSalesAddTicketQuantityActivity : YrcBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_sales_add_ticket_quantity)

        val i = intent
        val bundle = i.extras
        if (bundle != null) {
            val countAdultTickets = bundle.getInt(HomeFragment.TICKET_ADULTS)
            if (countAdultTickets != 0)
                textView_adult.text = countAdultTickets.toString().plus(" ")
                    .plus("x Adult £".plus(" ").plus(countAdultTickets.times(20)))

            val countOver65Tickets = bundle.getInt(HomeFragment.TICKET_OVER65)
            if (countOver65Tickets != 0)
                textView_over65.text = countOver65Tickets.toString().plus(" ")
                    .plus("x Over65 £".plus(" ").plus(countOver65Tickets.times(30)))

            val count1822Tickets = bundle.getInt(HomeFragment.TICKET_1822)
            if (count1822Tickets != 0)
                textView_1822.text = count1822Tickets.toString().plus(" ")
                    .plus("x 18-22 £".plus(" ").plus(count1822Tickets.times(40)))

            val countRacegoerTickets = bundle.getInt(HomeFragment.TICKET_RACEGOER)
            if (countRacegoerTickets != 0)
                textView_racegoer.text = countRacegoerTickets.toString().plus(" ")
                    .plus("x Racegoer £".plus(" ").plus(countRacegoerTickets.times(50)))

            textView_ticket.text = countAdultTickets.plus(countOver65Tickets).plus(count1822Tickets)
                .plus(countRacegoerTickets).toString().plus(" ")
                .plus(
                    "x Ticket £".plus(" ")
                        .plus(
                            countAdultTickets.times(20)
                                .plus(countOver65Tickets.times(30))
                                .plus(count1822Tickets.times(40))
                                .plus(countRacegoerTickets.times(50))
                        )
                )
        }

    }

    fun onPlusButtonClicked(view: View) {
        onBackPressed()
    }

    fun onMinusButtonClicked(view: View) {
        Toast.makeText(this, "onMinusButtonClicked", Toast.LENGTH_SHORT).show()
        //todo get selected ticket and removed its count or set count to 0
    }

    fun onMultiplyButtonClicked(view: View) {
        Toast.makeText(this, "onMultiplyButtonClicked", Toast.LENGTH_SHORT).show()
        //todo get selected ticket and increase its count
    }

    fun onCrossButtonClicked(view: View) {
        Toast.makeText(this, "onCrossButtonClicked", Toast.LENGTH_SHORT).show()
        //todo clear all tickets and return to back screen
        finish()
    }

    fun onCashButtonClicked(view: View) {
        Toast.makeText(this, "Printing tickets...", Toast.LENGTH_SHORT).show()
    }

}