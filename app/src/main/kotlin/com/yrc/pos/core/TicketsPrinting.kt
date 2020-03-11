package com.yrc.pos.core

import android.content.Context
import android.graphics.BitmapFactory
import com.pax.dal.IDAL
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.pax.dal.exceptions.PrinterDevException
import com.pax.neptunelite.api.NeptuneLiteUser
import com.yrc.pos.R
import java.text.DateFormat
import java.util.*

object TicketsPrinting {

    internal fun printAdultTicket(context: Context) {

        val dal: IDAL = NeptuneLiteUser.getInstance().getDal(context)
        val prn = dal.printer
        prn.init()

        prn.fontSet(EFontTypeAscii.FONT_24_48, EFontTypeExtCode.FONT_24_48)
        prn.leftIndent(110)
//            prn.spaceSet(50.toByte(), 50.toByte())
        prn.printStr("Adult", null)
        prn.printStr("\n", null)
        prn.leftIndent(100)

        prn.printStr("£" + Prices.PRICE_ADULT, null)
        prn.leftIndent(0)
        prn.printStr("----------------", null)

        prn.fontSet(EFontTypeAscii.FONT_16_32, EFontTypeExtCode.FONT_16_32)
        prn.leftIndent(20)
        prn.spaceSet(0.toByte(), 0.toByte())
        prn.printStr(DateFormat.getDateTimeInstance().format(Date()), null)
        prn.printStr("\n", null)

        prn.leftIndent(20)
        prn.printStr("Receipt only", null)
        prn.printStr("\n", null)

        prn.printStr("Not valid for entry", null)
        prn.printStr("\n", null)

        prn.fontSet(EFontTypeAscii.FONT_8_16, EFontTypeExtCode.FONT_16_16)
        prn.leftIndent(20)
        prn.dotLine
        prn.printStr("Retain ticket as a proof", null)

//            prn.invert(true)
        prn.step(10)
        prn.leftIndent(0)
        prn.printBitmapWithMonoThreshold(BitmapFactory.decodeResource(context.resources, R.drawable.logo), 1)

        val apiResult: Int = dal.printer.start()
        try {
            when (apiResult) {
                0 -> YrcLogger.d("Submission successfully made")
                1 -> YrcLogger.d("Busy, so far so good")
                2 -> YrcLogger.d("Out of paper")
                else -> YrcLogger.d("Unexpected")
            }
        } catch (ex: PrinterDevException) {
            ex.printStackTrace()
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

    internal fun printOver65Ticket(context: Context) {

        val dal: IDAL = NeptuneLiteUser.getInstance().getDal(context)
        val prn = dal.printer
        prn.init()

        prn.fontSet(EFontTypeAscii.FONT_24_48, EFontTypeExtCode.FONT_24_48)
        prn.leftIndent(110)
//            prn.spaceSet(50.toByte(), 50.toByte())
        prn.printStr("Over65", null)
        prn.printStr("\n", null)
        prn.leftIndent(100)

        prn.printStr("£" + Prices.PRICE_OVER65, null)
        prn.leftIndent(0)
        prn.printStr("----------------", null)

        prn.fontSet(EFontTypeAscii.FONT_16_32, EFontTypeExtCode.FONT_16_32)
        prn.leftIndent(20)
        prn.spaceSet(0.toByte(), 0.toByte())
        prn.printStr(DateFormat.getDateTimeInstance().format(Date()), null)
        prn.printStr("\n", null)

        prn.leftIndent(20)
        prn.printStr("Receipt only", null)
        prn.printStr("\n", null)

        prn.printStr("Not valid for entry", null)
        prn.printStr("\n", null)

        prn.fontSet(EFontTypeAscii.FONT_8_16, EFontTypeExtCode.FONT_16_16)
        prn.leftIndent(20)
        prn.dotLine
        prn.printStr("Retain ticket as a proof", null)

//            prn.invert(true)
        prn.step(10)
        prn.leftIndent(0)
        prn.printBitmapWithMonoThreshold(BitmapFactory.decodeResource(context.resources, R.drawable.logo), 1)

        val apiResult: Int = dal.printer.start()
        try {
            when (apiResult) {
                0 -> YrcLogger.d("Submission successfully made")
                1 -> YrcLogger.d("Busy, so far so good")
                2 -> YrcLogger.d("Out of paper")
                else -> YrcLogger.d("Unexpected")
            }
        } catch (ex: PrinterDevException) {
            ex.printStackTrace()
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
}