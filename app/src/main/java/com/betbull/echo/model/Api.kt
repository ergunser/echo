package com.betbull.echo.model

import android.database.Observable
import retrofit2.http.GET

interface Api {

    @GET("emredirican/mock-api/db")
    fun getItemsByName(): Observable<ResponseObject>
}
