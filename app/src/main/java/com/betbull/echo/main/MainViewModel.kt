package com.betbull.echo.main

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.betbull.echo.R
import com.betbull.echo.base.util.ResourceUtil
import com.betbull.echo.base.viewmodel.BaseViewModel
import com.betbull.echo.base.viewmodel.ToolbarViewModel
import com.betbull.echo.main.model.Api
import com.betbull.echo.main.model.ResponseItem
import com.betbull.echo.main.model.ResponseObject
import com.betbull.echo.main.view.ListItemViewHolder
import com.betbull.echo.main.viewmodel.ListItemViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel : BaseViewModel(), SocketInteractListener {

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var socketInteract: SocketInteract

    val list = MutableLiveData<MutableList<ListItemViewHolder>>()
    val progressBarVisible = MutableLiveData<Boolean>().apply { value = true }
    val editTextField: ObservableField<String> = ObservableField()

    override val toolbarViewModel: ToolbarViewModel? = ToolbarViewModel()

    init {
        fetchItemList()
        socketInteract.listener = this
    }

    fun onClickSendButton() {
        socketInteract.sendMessage(editTextField.get().orEmpty())
    }

    override fun onUserLogout() {
        toolbarViewModel?.title?.value = ResourceUtil.getString(R.string.app_name)
    }

    override fun onUserLogin() {
        toolbarViewModel?.title?.value = ResourceUtil.getString(R.string.welcome_message)
    }

    override fun onMockListUpdated(mockList: MutableList<ResponseItem>?) {
        updateAdapterList(mockList)
    }

    override fun onError(errorMessage: String) {
        this.errorMessage.value = errorMessage
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

        updateAdapterList(responseObject?.data)

        socketInteract.mockList = responseObject?.data as? MutableList<ResponseItem>?
        socketInteract.subscribeToSocket()

        progressBarVisible.value = false
    }

    private fun handleError() {
        progressBarVisible.value = false

        errorMessage.value = ResourceUtil.getString(R.string.error_mock_message)
    }

    private fun updateAdapterList(responseList: List<ResponseItem>?) {
        val list = mutableListOf<ListItemViewHolder>()

        responseList?.map {
            ListItemViewModel(it)
        }?.mapTo(list) { ListItemViewHolder(it) }

        this.list.value = list

    }

}
