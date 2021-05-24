package com.casper.currencyconverterfixer.network

import com.casper.currencyconverterfixer.model.Currency
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*


interface CurrencyService {

    //    @GET("symbols")
//    fun getSymbolsAsync(@Query("access_key") access_key: String,): Deferred<List<Currency>>
    @GET("symbols")
    fun getSymbolsAsync(@Query("access_key") access_key: String,): Call<ServerResponse>
}