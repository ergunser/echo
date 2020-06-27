package com.betbull.echo.base.util

import android.content.Context
import androidx.annotation.StringRes
import com.betbull.echo.base.BetbullEchoApp

object ResourceUtil {

    private val context: Context
        get() = BetbullEchoApp.getInstance()?.applicationContext!!

    fun getString(@StringRes resId: Int) = context.getString(resId)

    fun getString(@StringRes resId: Int, vararg formatArgs: Any) = context.getString(resId, *formatArgs)


}
