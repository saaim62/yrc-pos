package com.yrc.pos.core

import android.content.Context
import android.graphics.BitmapFactory
import com.pax.dal.IDAL
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import com.pax.neptunelite.api.NeptuneLiteUser
import com.yrc.pos.R
import com.yrc.pos.core.session.User
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

        prn.printStr("£" + User.getUserPrice()?.adultPrice?.toInt(), null)
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

        prn.step(10)
        prn.leftIndent(100)
//        prn.invert(true)
        prn.printBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.qrcode))
        prn.step(100)

//        val apiResult: Int = dal.printer.start()
//        try {
//            when (apiResult) {
//                0 -> Toast.makeText(context, "Submission successfully made", Toast.LENGTH_SHORT).show()
//                1 -> Toast.makeText(context, "Busy, so far so good", Toast.LENGTH_SHORT).show()
//                2 -> Toast.makeText(context, "Out of paper", Toast.LENGTH_SHORT).show()
//                else -> Toast.makeText(context, "Unexpected", Toast.LENGTH_SHORT).show()
//            }
//        } catch (ex: PrinterDevException) {
//            ex.printStackTrace()
//        }

//        do {// Check every quarter-second for result of print. 
//            Thread.sleep(250)
//            apiResult = prn.status
//        } while (apiResult == 1)
//        // Paper cutter. 
//        try {
//            val cutMode: Int = prn.cutMode
//            if ((cutMode == 0) || (cutMode == 2)) {
//                // 0=full, or 2=partial/full => full cut. 
//                prn.cutPaper(0)
//            } else if (cutMode == 1) {
//                // 1=partial only => partial cut. 
//                prn.cutPaper(1)
//            }
//        } catch (pdex: PrinterDevException) {
//        }
    }

    internal fun printOver65Ticket(context: Context) {
        
        val dal: IDAL = NeptuneLiteUser.getInstance().getDal(context)
        val prn = dal.printer
        prn.init()

        prn.fontSet(EFontTypeAscii.FONT_24_48, EFontTypeExtCode.FONT_24_48)
        prn.leftIndent(110)
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

        prn.step(10)
        prn.leftIndent(100)
        prn.printBitmapWithMonoThreshold(BitmapFactory.decodeResource(context.resources, R.drawable.qrcode), 1)
        prn.step(100)
    }
}