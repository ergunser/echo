package com.betbull.echo.base

import android.app.Application


class BetbullEchoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    companion object {

        private var instance: BetbullEchoApp? = null

        fun getInstance(): BetbullEchoApp? {
            return instance
        }
    }
}
