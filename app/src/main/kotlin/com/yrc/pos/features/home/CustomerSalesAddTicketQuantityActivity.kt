package com.yrc.pos.features.home

import android.os.Bundle
import android.view.View
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseActivity

class CustomerSalesAddTicketQuantityActivity : YrcBaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_sales_add_ticket_quantity)
    }

    fun onCrossButtonClicked(view: View) {
        finish()
    }

    fun onCashButtonClicked(view: View) {
        finish()
    }

}