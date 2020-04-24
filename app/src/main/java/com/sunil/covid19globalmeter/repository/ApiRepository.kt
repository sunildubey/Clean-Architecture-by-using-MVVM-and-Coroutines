package com.sunil.covid19globalmeter.repository

import com.sunil.covid19globalmeter.api.WebService
import com.sunil.covid19globalmeter.model.DashboardModel
import com.sunil.covid19globalmeter.model.DateWiseCountModel
import com.sunil.covid19globalmeter.model.DateWiseCountModelItem
import com.sunil.covid19globalmeter.model.TotalDataModel
import com.sunil.covid19globalmeter.utilities.APiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class ApiRepository(private val apiService: WebService) {

    /*
    * get total count around the world
    * */
    fun getDashBoard(): Flow<APiState<DashboardModel>> {
        return object : NetworkBoundRepository<DashboardModel>() {
            override suspend fun fetchFromRemote(): Response<DashboardModel> =
                apiService.getDashBoardData()
        }.asFlow().flowOn(Dispatchers.IO)
    }

    /*
    * date wise progression of covid19 cases
    * */
    fun getDateWise(countryName: String): Flow<APiState<List<DateWiseCountModelItem>>> {
        return object : NetworkBoundRepository<List<DateWiseCountModelItem>>() {
            override suspend fun fetchFromRemote(): Response<List<DateWiseCountModelItem>> =
                apiService.getDateWise(countryName);
        }.asFlow().flowOn(Dispatchers.IO)
    }

    /*
    * get total
    * */
    fun getTotal(): Flow<APiState<TotalDataModel>> {
        return object : NetworkBoundRepository<TotalDataModel>() {
            override suspend fun fetchFromRemote(): Response<TotalDataModel> =
                apiService.getTotal()
        }.asFlow().flowOn(Dispatchers.IO)
    }
}