package com.betbull.echo.main

import android.annotation.SuppressLint
import com.betbull.echo.base.Constants
import com.betbull.echo.base.extensions.orFalse
import com.betbull.echo.main.model.ResponseItem
import com.betbull.echo.main.model.SocketRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SocketInteract @Inject constructor() {

    @Inject
    lateinit var repository: SocketRepository

    var listener: SocketInteractListener? = null

    var mockList: MutableList<ResponseItem>? = null


    @SuppressLint("CheckResult")
    fun subscribeToSocket() {
        repository.messageObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                handleSocketMessage(it)
            }
        //FIXME auto dispose
    }

    fun sendMessage(message: String) {
        repository.sendMessage(message)
    }

    private fun handleSocketMessage(text: String?) {

        when (text) {
            LOGIN_MESSAGE -> {
                listener?.onUserLogin()
            }
            LOGOUT_MESSAGE -> {
                listener?.onUserLogout()
            }
            else -> {
                updateListItemIfValid(text)
            }
        }
    }


    private fun updateListItemIfValid(text: String?) {
        val pair = convertMessageToPair(text)

        if (pair == null) {
            //FIXME show invalid input & socket error
            return
        }

        mockList?.find { it.id == pair.first }?.name = pair.second


        listener?.onMockListUpdated(mockList)

    }

    private fun convertMessageToPair(text: String?): Pair<Int, String>? {

        if (!text?.contains(Constants.MESSAGE_SEPARATOR).orFalse()) {
            return null
        }

        val id = text?.substring(0, text.indexOf(Constants.MESSAGE_SEPARATOR))

        val idInt = id?.toIntOrNull() ?: return null

        val name = text.substring(text.indexOf(Constants.MESSAGE_SEPARATOR) + 1)

        return if (name.isNotEmpty()) {
            Pair(idInt, name)
        } else {
            null
        }

    }


}
