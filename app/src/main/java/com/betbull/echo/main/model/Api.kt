package com.betbull.echo.main.model

import io.reactivex.Observable
import retrofit2.http.GET

interface Api {

    @GET("emredirican/mock-api/db")
    fun fetchItemsByName(): Observable<ResponseObject>
}
