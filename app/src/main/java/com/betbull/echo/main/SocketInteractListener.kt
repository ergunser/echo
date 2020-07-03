package com.betbull.echo.main

import com.betbull.echo.main.model.ResponseItem

interface SocketInteractListener {

    fun onUserLogin()

    fun onUserLogout()

    fun onMockListUpdated(mockList: MutableList<ResponseItem>?)
}
