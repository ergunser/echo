package com.betbull.echo.base.viewmodel

import com.betbull.echo.MainViewModel
import com.betbull.echo.base.network.NetworkModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(mainViewModel: MainViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
    }
}
