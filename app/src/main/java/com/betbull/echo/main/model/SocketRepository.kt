package com.betbull.echo.main.model

import android.util.Log
import com.betbull.echo.base.Constants.SOCKET_URL
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class SocketRepository : WebSocketListener() {

    private var client: OkHttpClient? = null
    private var webSocket: WebSocket? = null

    private val messageSubject: Subject<String> = PublishSubject.create()
    val messageObservable: Observable<String> = messageSubject

    init {
        client = OkHttpClient()
        val request: Request = Request.Builder().url(SOCKET_URL).build()
        webSocket = client?.newWebSocket(request, this)
        //client?.dispatcher()?.executorService()?.shutdown()

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        messageSubject.onNext(text)
        Log.i("erguns", "onMessage $text")
    }

    fun sendMessage(text: String) {
        webSocket?.send(text)
    }

}
