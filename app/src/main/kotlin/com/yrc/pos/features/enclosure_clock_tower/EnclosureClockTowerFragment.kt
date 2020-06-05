package com.yrc.pos.features.enclosure_clock_tower

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseFragment
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
import com.yrc.pos.core.session.User
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_enclosure_g_and_p.*

class EnclosureClockTowerFragment : YrcBaseFragment() {

    private var countAdultTickets = 0
    private var countOver65Tickets = 0

    private lateinit var disposableClearAllTickets: Disposable
    private lateinit var disposableMultiplyTicket: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_enclosure_clock_tower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView1()
        setAdultButtonListener()
        setOver65ButtonListener()
        setTotalButtonListener()

        disposableClearAllTickets = RxBus.listen(RxEvent.buttonFunction::class.java).subscribe {
            countAdultTickets = 0
            countOver65Tickets = 0
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
            }
        }
    }

    private fun initView1() {
        button_Adult.text= "Adult   £" + User.getUserPrice()?.adultPrice?.toInt()
        button_Over65.text = "Over65   £" + User.getUserPrice()?.over65Price?.toInt()
    }

    override fun onResume() {
        super.onResume()
        button_total.text = countAdultTickets.plus(countOver65Tickets).toString().plus("")
            .plus(
                "x Ticket £".plus(
                    User.getUserPrice()?.adultPrice?.toInt().let {
                        if (it != null) {
                            countAdultTickets.times(it)
                                .plus(countOver65Tickets.times(User.getUserPrice()?.over65Price?.toInt()))
                        }
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
            val intent = Intent(activity, EnclosureClockTowerPrintingActivity::class.java)
            countAdultTickets += 1
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            startActivity(intent)
        }
    }

    private fun setOver65ButtonListener() {
        button_Over65.setOnClickListener {
            val intent = Intent(activity, EnclosureClockTowerPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            countOver65Tickets += 1
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            startActivity(intent)
        }
    }

    private fun setTotalButtonListener() {
        button_total.setOnClickListener {
            val intent = Intent(activity, EnclosureClockTowerPrintingActivity::class.java)
            intent.putExtra(TICKET_ADULTS, countAdultTickets)
            intent.putExtra(TICKET_OVER65, countOver65Tickets)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        val TICKET_ADULTS = "ticket_adults"
        @JvmStatic
        val TICKET_OVER65 = "ticket_over65"
    }
}