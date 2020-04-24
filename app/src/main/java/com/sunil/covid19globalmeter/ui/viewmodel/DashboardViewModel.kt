package com.sunil.covid19globalmeter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunil.covid19globalmeter.model.DashboardModel
import com.sunil.covid19globalmeter.repository.ApiRepository
import com.sunil.covid19globalmeter.utilities.APiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DashboardViewModel(private val repository: ApiRepository) : ViewModel() {

    private val dashBoardLiveData = MutableLiveData<APiState<DashboardModel>>()

    val holdLiveData: LiveData<APiState<DashboardModel>> get() = dashBoardLiveData

    fun getDashBoard() {
        viewModelScope.launch {
            repository.getDashBoard().collect {
                dashBoardLiveData.value = it
            }
        }
    }
}
