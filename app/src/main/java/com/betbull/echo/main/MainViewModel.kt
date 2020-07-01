package com.betbull.echo.main

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.betbull.echo.base.viewmodel.BaseViewModel
import com.betbull.echo.main.model.Api
import com.betbull.echo.main.model.ResponseObject
import com.betbull.echo.main.model.SocketRepository
import com.betbull.echo.main.view.ListItemViewHolder
import com.betbull.echo.main.viewmodel.ListItemViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var api: Api

    private val repository = SocketRepository() // FIXME use dagger

    val list = MutableLiveData<MutableList<ListItemViewHolder>>()
    val progressBarVisible = MutableLiveData<Boolean>().apply { value = true }
    val editTextField: ObservableField<String> = ObservableField()

    init {
        fetchItemList()
        subscribeToSocket()

        repository.sendMessage("hello") // FIXME connect this to edittext
    }

    fun onClickSendButton() {
        //FIXME send edittextField to socket
    }

    private fun subscribeToSocket() {
        repository.messageObservable.subscribe {
            Log.i("erguns", "observed: $it")
        }.autoDispose()
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
