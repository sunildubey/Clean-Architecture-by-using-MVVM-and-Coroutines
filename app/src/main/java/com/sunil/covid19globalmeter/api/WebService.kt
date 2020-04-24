package com.sunil.covid19globalmeter.api

import com.sunil.covid19globalmeter.model.DashboardModel
import com.sunil.covid19globalmeter.model.DateWiseCountModelItem
import com.sunil.covid19globalmeter.model.TotalDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    @GET("summary")
    suspend fun getDashBoardData(): Response<DashboardModel>

    @GET("total/country//{slug}")
    suspend fun getDateWise(@Path("slug") name: String): Response<List<DateWiseCountModelItem>>

    @GET("world/total")
    suspend fun getTotal(): Response<TotalDataModel>

    companion object {
        const val BASE_URL = "https://api.covid19api.com/"
    }
}
