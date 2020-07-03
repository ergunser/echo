package com.betbull.echo.base.viewmodel

import androidx.lifecycle.ViewModel
import com.betbull.echo.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .build()

    private val disposeBag = CompositeDisposable()

    open val toolbarViewModel: ToolbarViewModel? = null

    init {
        inject()
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }

    private fun inject() {
        when (this) {
            is MainViewModel -> injector.inject(this)
        }
    }

    protected fun Disposable.autoDispose() {
        disposeBag.add(this)
    }

}
