package com.yrc.pos.features.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pax.dal.IDAL
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.pax.dal.exceptions.PrinterDevException
import com.pax.neptunelite.api.NeptuneLiteUser
import com.yrc.pos.R
import com.yrc.pos.core.Prices
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.bus.RxBus
import com.yrc.pos.core.bus.RxEvent
import kotlinx.android.synthetic.main.activity_customer_sales_add_ticket_quantity.*


class CustomerSalesAddTicketQuantityActivity : YrcBaseActivity() {
    private var countAdultTickets: Int = 0
    private var countOver65Tickets: Int = 0
    private var count1822Tickets: Int = 0
    private var countRacegoerTickets: Int = 0

    private var selectedButton: Int = 0

    lateinit var dal: IDAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_sales_add_ticket_quantity)
        val i = intent
        val bundle = i.extras
        if (bundle != null) {
            countAdultTickets = bundle.getInt(DashboardActivity.TICKET_ADULTS)
            countOver65Tickets = bundle.getInt(DashboardActivity.TICKET_OVER65)
            count1822Tickets = bundle.getInt(DashboardActivity.TICKET_1822)
            countRacegoerTickets = bundle.getInt(DashboardActivity.TICKET_RACEGOER)
        }

        button_adult.setOnClickListener {
            selectedButton = 1
        }
        button_over65.setOnClickListener {
            selectedButton = 2
        }
        button_1822.setOnClickListener {
            selectedButton = 3
        }
        button_racegoer.setOnClickListener {
            selectedButton = 4
        }
        button_cash.setOnClickListener {
            Toast.makeText(this, "Printing...", Toast.LENGTH_SHORT).show()

            dal = NeptuneLiteUser.getInstance().getDal(this)
            var prn = dal.printer
            prn.init()
            prn.fontSet(EFontTypeAscii.FONT_16_16, EFontTypeExtCode.FONT_16_16)
            prn.printStr("Hello World!", null)

//            prn.spaceSet((byte)0,(byte)0); 
//            prn.step(0);
//            prn.invert(doInvert);
//            prn.leftIndent(leftMarginPx);
//            prn.printStr("Hello World2!", null)

            val apiResult: Int = dal.printer.start()

//            try {
//
//                when (apiResult) {
//                         
//                         
//                          0
//                    -> // Submission successfully made.
//                         
//                         
//                          1
//                    ->// Busy, so far so good.
//                         
//                         
//                         2
//                    ->// Out of paper.
//                         
//                         
//                         
//                    else ->// Hmm
//                         
//                         
//                         
//                }
//            } catch (PrinterDevException ex) {
//            }
//            do {
//                 
//                    // Check every quarter-second for result of print. 
//                    Thread.sleep(250); 
//                    apiResult = prn.getStatus(); 
//            } while (apiResult == 1);
//
//// Paper cutter. 
//            try {
//                 
//                    int cutMode = prn.getCutMode(); 
//                    if ((cutMode == 0) || (cutMode == 2)) {
//                     
//                            // 0=full, or 2=partial/full => full cut. 
//                            prn.cutPaper(0); 
//                       
//                } 
//                    else if (cutMode == 1) {
//                     
//                            // 1=partial only => partial cut. 
//                            prn.cutPaper(1); 
//                       
//                } 
//            } 
//            catch(PrinterDevException pdex) {
//                 
//            }


        }

        updateUi()
    }

    fun onPlusButtonClicked(view: View) {
        onBackPressed()
    }

    fun onMinusButtonClicked(view: View) {
        when (selectedButton) {
            0 -> {
                Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
            }
            1 -> {
                countAdultTickets -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        DashboardActivity.TICKET_ADULTS,
                        countAdultTickets
                    )
                )
                updateUi()
            }
            2 -> {
                countOver65Tickets -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        DashboardActivity.TICKET_OVER65,
                        countOver65Tickets
                    )
                )
                updateUi()
            }
            3 -> {
                count1822Tickets -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        DashboardActivity.TICKET_1822,
                        count1822Tickets
                    )
                )
                updateUi()
            }
            4 -> {
                countRacegoerTickets -= 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        DashboardActivity.TICKET_RACEGOER,
                        countRacegoerTickets
                    )
                )
                updateUi()
            }
        }
    }

    fun onMultiplyButtonClicked(view: View) {
        when (selectedButton) {
            0 -> {
                Toast.makeText(this, "Please select ticket", Toast.LENGTH_SHORT).show()
            }
            1 -> {
                countAdultTickets += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        DashboardActivity.TICKET_ADULTS,
                        countAdultTickets
                    )
                )
                updateUi()
            }
            2 -> {
                countOver65Tickets += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        DashboardActivity.TICKET_OVER65,
                        countOver65Tickets
                    )
                )
                updateUi()
            }
            3 -> {
                count1822Tickets += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        DashboardActivity.TICKET_1822,
                        count1822Tickets
                    )
                )
                updateUi()
            }
            4 -> {
                countRacegoerTickets += 1
                RxBus.publish(
                    RxEvent.setTicketCount(
                        DashboardActivity.TICKET_RACEGOER,
                        countRacegoerTickets
                    )
                )
                updateUi()
            }
        }
    }

    fun onCrossButtonClicked(view: View) {
        //todo clear all tickets and return to back screen
        RxBus.publish(RxEvent.buttonFunction("onCrossButtonClicked"))
        finish()
    }

    fun onCashButtonClicked(view: View) {
        finish()
    }

    private fun updateUi() {
        if (countAdultTickets != 0)
            button_adult.visibility = View.VISIBLE
        button_adult.text = countAdultTickets.toString().plus(" ")
            .plus("x Adult £".plus(" ").plus(countAdultTickets.times(Prices.PRICE_ADULT)))

        if (countOver65Tickets != 0)
            button_over65.visibility = View.VISIBLE
        button_over65.text = countOver65Tickets.toString().plus(" ")
            .plus("x Over65 £".plus(" ").plus(countOver65Tickets.times(Prices.PRICE_OVER65)))

        if (count1822Tickets != 0)
            button_1822.visibility = View.VISIBLE
        button_1822.text = count1822Tickets.toString().plus(" ")
            .plus("x 18-22 £".plus(" ").plus(count1822Tickets.times(Prices.PRICE_1822)))

        if (countRacegoerTickets != 0)
            button_racegoer.visibility = View.VISIBLE
        button_racegoer.text = countRacegoerTickets.toString().plus(" ")
            .plus("x Racegoer £".plus(" ").plus(countRacegoerTickets.times(Prices.PRICE_RACEGOER)))

        textView_ticket.text = countAdultTickets.plus(countOver65Tickets).plus(count1822Tickets)
            .plus(countRacegoerTickets).toString().plus(" ")
            .plus(
                "x Ticket £".plus(" ")
                    .plus(
                        countAdultTickets.times(Prices.PRICE_ADULT)
                            .plus(countOver65Tickets.times(Prices.PRICE_OVER65))
                            .plus(count1822Tickets.times(Prices.PRICE_1822))
                            .plus(countRacegoerTickets.times(Prices.PRICE_RACEGOER))
                    )
            )

    }

}