package com.yrc.pos.core.bus

class RxEvent {
    data class buttonFunction(val buttonName: String)
    data class setTicketCount(val ticketName: String, val count: Int)
}