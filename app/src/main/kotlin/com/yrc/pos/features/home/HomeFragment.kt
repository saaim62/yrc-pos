package com.yrc.pos.features.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yrc.pos.R
import com.yrc.pos.core.YrcBaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : YrcBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            startActivity(intent)
        }
    }

    private fun setOver65ButtonListener() {
        button_Over65.setOnClickListener {
            val intent = Intent(activity, CustomerSalesAddTicketQuantityActivity::class.java)
            startActivity(intent)
        }
    }

    private fun set1822ButtonListener() {
        button_1822.setOnClickListener {
            val intent = Intent(activity, CustomerSalesAddTicketQuantityActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRacegoerButtonListener() {
        button_racegoer.setOnClickListener {
            val intent = Intent(activity, CustomerSalesAddTicketQuantityActivity::class.java)
            startActivity(intent)
        }
    }

}