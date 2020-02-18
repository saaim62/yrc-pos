package com.yrc.pos.features.dashboard

import android.graphics.BitmapFactory
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
import java.text.DateFormat
import java.util.*


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
            val prn = dal.printer
            prn.init()
            prn.fontSet(EFontTypeAscii.FONT_24_48, EFontTypeExtCode.FONT_24_48)
            prn.leftIndent(100)
//            prn.spaceSet(50.toByte(), 50.toByte())
            prn.printStr("Adult", null)
            prn.printStr("\n", null)
            prn.printStr("£20.00", null)
            prn.printStr("\n", null)

            prn.leftIndent(0)
            prn.spaceSet(0.toByte(), 0.toByte())
            prn.fontSet(EFontTypeAscii.FONT_16_16, EFontTypeExtCode.FONT_16_32)
            prn.printStr(DateFormat.getDateTimeInstance().format(Date()), null)
            prn.printStr("\n", null)
            prn.printStr("Receipt only", null)
            prn.printStr("\n", null)
            prn.printStr("Not valid for entry", null)
            prn.printStr("\n", null)
            prn.fontSet(EFontTypeAscii.FONT_8_16, EFontTypeExtCode.FONT_16_16)
            prn.printStr("Retain ticket as a proof", null)
            prn.printStr("\n", null)
            prn.invert(true)
//            prn.printBitmap(BitmapFactory.decodeResource(resources, R.drawable.qrcode))
            prn.printStr("\n", null)
            prn.printStr("\n", null)
            prn.printStr("\n", null)


//            prn.printStr("========================", null)
//            prn.printStr("\n", null)
//            prn.printStr("Start:", null)
//            prn.printStr("\n", null)
//            prn.printStr("End:", null)
//            prn.printStr("\n", null)
//            prn.printStr("Driver:", null)
//            prn.printStr("\n", null)
//            prn.spaceSet(0.toByte(),0.toByte())
//            prn.printStr("Duty:", null)
//            prn.printStr("\n", null)
//            prn.printStr("S.Serial:", null)
//            prn.printStr("\n", null)
//            prn.step(0)
//            prn.printStr("E.Serial:", null)
//            prn.printStr("\n", null)
//            prn.spaceSet(0.toByte(),0.toByte())
//            prn.printStr("Machine:", null)
//            prn.printStr("\n", null)
//            prn.printStr("------------------------", null)
//            prn.printStr("\n", null)
//            prn.printStr("SALES", null)
//            prn.printStr("\n", null)
//            prn.printStr("Adult", null)
//            prn.printStr("\n", null)
////            prn.invert(true)
//            prn.printStr("Over 65", null)
//            prn.printStr("\n", null)
//            prn.printStr("CASH TOTAL", null)
//            prn.printStr("\n", null)
//            prn.printStr("Gross:", null)
//            prn.printStr("\n", null)
//            prn.leftIndent(3)
//            prn.printStr("Annualled:", null)
//            prn.printStr("\n", null)
//            prn.printStr("Net:", null)
//            prn.printStr("\n", null)
//            prn.printStr("------------------------", null)

            var apiResult: Int = dal.printer.start()

            try {

                when (apiResult) {
                    0 -> Toast.makeText(this, "Submission successfully made", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(this, "Busy, so far so good", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this, "Out of paper", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this, "Unexpected", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: PrinterDevException) {
            }
//            do {// Check every quarter-second for result of print. 
//                Thread.sleep(250)
//                apiResult = prn.status
//            } while (apiResult == 1)
//            // Paper cutter. 
//            try {
//                val cutMode: Int = prn.getCutMode()
//                if ((cutMode == 0) || (cutMode == 2)) {
//                    // 0=full, or 2=partial/full => full cut. 
//                    prn.cutPaper(0)
//                } else if (cutMode == 1) {
//                    // 1=partial only => partial cut. 
//                    prn.cutPaper(1)
//                }
//            } catch (pdex: PrinterDevException) {
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