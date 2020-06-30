package com.betbull.echo.main

import androidx.lifecycle.MutableLiveData
import com.betbull.echo.base.viewmodel.BaseViewModel
import com.betbull.echo.main.model.Api
import com.betbull.echo.main.model.ResponseObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var api: Api

    val list = MutableLiveData<MutableList<ListItemViewHolder>>()
    val progressBarVisible = MutableLiveData<Boolean>().apply { value = true }

    init {
        fetchItemList()
    }

    private fun fetchItemList() {

        api.fetchItemsByName()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                handleResponse(it)

            }, {

                handleError()

            }).autoDispose()

    }

    private fun handleResponse(responseObject: ResponseObject?) {

        val list = mutableListOf<ListItemViewHolder>()

        responseObject?.data?.map {
            ListItemViewModel(it.name)
        }?.mapTo(list) { ListItemViewHolder(it) }

        this.list.value = list

        progressBarVisible.value = false
    }

    private fun handleError() {
        progressBarVisible.value = false

        //FIXME handle Error
    }

}
