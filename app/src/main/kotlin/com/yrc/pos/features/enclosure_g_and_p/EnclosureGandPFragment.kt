package com.yrc.pos.features.enclosure_g_and_p

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.yrc.pos.R
import com.yrc.pos.core.*
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
import com.yrc.pos.core.views.YrcTextView
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_enclosure_g_and_p_printing.*
import kotlinx.android.synthetic.main.fragment_enclosure_g_and_p.*
import kotlinx.android.synthetic.main.fragment_enclosure_g_and_p.button_1822
import kotlinx.android.synthetic.main.fragment_enclosure_g_and_p.button_racegoer

class EnclosureGandPFragment : YrcBaseFragment() {

    private var countAdultTickets = 0
    private var countOver65Tickets = 0
    private var count1822Tickets = 0
    private var countRacegoerTickets = 0

    private lateinit var textViewHeaderTitle: YrcTextView

    private lateinit var disposableClearAllTickets: Disposable
    private lateinit var disposableMultiplyTicket: Disposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_enclosure_g_and_p, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
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
            Toast.makeText(activity, "Cleared all selections and reset", Toast.LENGTH_SHORT).show()
        }
        disposableMultiplyTicket = RxBus.listen(RxEvent.setTicketCount::class.java).subscribe {
            when (it.ticketName) {
                TICKET_ADULTS -> {
                    countAdultTickets = it.count
                }
                TICKET_OVER65 -> {
                    countOver65Tickets = it.count
                }
                TICKET_1822 -> {
                    count1822Tickets = it.count
                }
                TICKET_RACEGOER -> {
                    countRacegoerTickets = it.count
                }
            }
        }
    }

    private fun initViews() {
        button_Adult.text = "Adult   £" + Prices.PRICE_ADULT
        button_Over65.text = "Over65   £" + Prices.PRICE_OVER65
        button_1822.text = "18-22   £" + Prices.PRICE_1822
        button_racegoer.text = "Racegoer  £" + Prices.PRICE_RACEGOER
    }

    override fun onResume() {
        super.onResume()
        button_total.text = countAdultTickets.plus(countOver65Tickets).plus(count1822Tickets)
            .plus(countRacegoerTickets).toString().plus(" ")
            .plus(
                "x Ticket £".plus(
                    Prices.PRICE_ADULT?.let {
                        countAdultTickets.times(it)
                            .plus(countOver65Tickets.times(Prices.PRICE_OVER65))
                            .plus(count1822Tickets.times(Prices.PRICE_1822))
                            .plus(countRacegoerTickets.times(Prices.PRICE_RACEGOER))
                    }
                )
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposableClearAllTickets.isDisposed) disposableClearAllTickets.dispose()
        if (!disposableMultiplyTicket.isDisposed) disposableMultiplyTicket.dispose()
    }

    private fun setAdultButtonListener() {
        button_Adult.setOnClickListener {
            val intent = Intent(activity, EnclosureGandPPrintingActivity::class.java)
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
            val intent = Intent(activity, EnclosureGandPPrintingActivity::class.java)
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
            val intent = Intent(activity, EnclosureGandPPrintingActivity::class.java)
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
            val intent = Intent(activity, EnclosureGandPPrintingActivity::class.java)
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
            val intent = Intent(activity, EnclosureGandPPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            intent.putExtra(TICKET_1822, count1822Tickets)
            intent.putExtra(TICKET_RACEGOER, countRacegoerTickets)
            startActivity(intent)
        }
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
}