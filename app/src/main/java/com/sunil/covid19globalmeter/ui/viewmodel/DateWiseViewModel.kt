package com.sunil.covid19globalmeter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunil.covid19globalmeter.model.DateWiseCountModel
import com.sunil.covid19globalmeter.model.DateWiseCountModelItem
import com.sunil.covid19globalmeter.repository.ApiRepository
import com.sunil.covid19globalmeter.utilities.APiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DateWiseViewModel(private val repository: ApiRepository) : ViewModel() {

    private val dateWiseLiveData = MutableLiveData<APiState<List<DateWiseCountModelItem>>>()

    val holdLiveData: LiveData<APiState<List<DateWiseCountModelItem>>> get() = dateWiseLiveData

    fun getDateWise(countryName : String) {
        viewModelScope.launch {
            repository.getDateWise(countryName).collect {
                dateWiseLiveData.value = it
            }
        }
    }
}
