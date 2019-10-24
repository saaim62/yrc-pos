package com.yrc.pos.features.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : YrcBaseFragment() {

    private var countAdultTickets = 0
    private var countOver65Tickets = 0
    private var count1822Tickets = 0
    private var countRacegoerTickets = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdultButtonListener()
        setOver65ButtonListener()
        set1822ButtonListener()
        setRacegoerButtonListener()
    }

    private fun setAdultButtonListener() {
        button_Adult.setOnClickListener {
            val intent = Intent(activity, CustomerSalesAddTicketQuantityActivity::class.java)
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
            val intent = Intent(activity, CustomerSalesAddTicketQuantityActivity::class.java)
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
            val intent = Intent(activity, CustomerSalesAddTicketQuantityActivity::class.java)
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
            val intent = Intent(activity, CustomerSalesAddTicketQuantityActivity::class.java)
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

    fun doThis(){
        //does something amazing!
        countAdultTickets = 0
        countOver65Tickets = 0
        count1822Tickets = 0
        countRacegoerTickets = 0
    }

//    override fun onResume() {
//        super.onResume()
//        if (true){
//        countAdultTickets = 0
//        countOver65Tickets = 0
//        count1822Tickets = 0
//        countRacegoerTickets = 0
//        }
//    }

    companion object {
        const val TICKET_ADULTS = "ticket_adults"
        const val TICKET_OVER65 = "ticket_over65"
        const val TICKET_1822 = "ticket_1822"
        const val TICKET_RACEGOER = "ticket_racegoer"
    }

}