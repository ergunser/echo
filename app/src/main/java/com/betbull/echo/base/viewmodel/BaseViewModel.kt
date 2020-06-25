package com.betbull.echo.base.viewmodel

import androidx.lifecycle.ViewModel
import com.betbull.echo.MainViewModel
import com.betbull.echo.base.network.NetworkModule

abstract class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainViewModel -> injector.inject(this)
        }
    }

}
