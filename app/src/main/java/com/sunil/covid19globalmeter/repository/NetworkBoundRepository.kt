package com.sunil.covid19globalmeter.repository

import androidx.annotation.MainThread
import com.sunil.covid19globalmeter.utilities.APiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<T> {

    fun asFlow() = flow<APiState<T>> {

        // Emit Loading State
        emit(APiState.loading())

        try {
            // Fetch latest data from remote
            val apiResponse = fetchFromRemote()

            // Parse body
            val remotePosts = apiResponse.body()

            // Check for response validation
            if (apiResponse.isSuccessful && remotePosts != null) {
                emit(APiState.success(remotePosts))
            } else {
                // Something went wrong! Emit Error state.
                emit(APiState.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            // Exception occurred! Emit error
            emit(APiState.error("Network error! Can't get latest data."))
            e.printStackTrace()
        }
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<T>
}