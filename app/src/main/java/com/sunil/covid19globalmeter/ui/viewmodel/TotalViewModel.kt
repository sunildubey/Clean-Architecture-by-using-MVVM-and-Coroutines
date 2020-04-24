package com.sunil.covid19globalmeter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunil.covid19globalmeter.model.TotalDataModel
import com.sunil.covid19globalmeter.repository.ApiRepository
import com.sunil.covid19globalmeter.utilities.APiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TotalViewModel(private val repository: ApiRepository) : ViewModel() {

    private val totalLiveData = MutableLiveData<APiState<TotalDataModel>>()

    val holdLiveData: LiveData<APiState<TotalDataModel>> get() = totalLiveData

    fun getDashBoard() {
        viewModelScope.launch {
            repository.getTotal().collect {
                totalLiveData.value = it
            }
        }
    }
}