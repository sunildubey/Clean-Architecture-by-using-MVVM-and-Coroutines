package com.sunil.covid19globalmeter.di

import com.sunil.covid19globalmeter.ui.viewmodel.DashboardViewModel
import com.sunil.covid19globalmeter.ui.viewmodel.DateWiseViewModel
import com.sunil.covid19globalmeter.ui.viewmodel.TotalViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val viewModelModule = module {
    viewModel {
        DashboardViewModel(get())

    }
    viewModel {
        DateWiseViewModel(get())

    }

    viewModel {
        TotalViewModel(get())

    }
}