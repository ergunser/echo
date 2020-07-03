package com.betbull.echo.main

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.betbull.echo.R
import com.betbull.echo.base.Constants.MESSAGE_SEPARATOR
import com.betbull.echo.base.extensions.orFalse
import com.betbull.echo.base.util.ResourceUtil
import com.betbull.echo.base.viewmodel.BaseViewModel
import com.betbull.echo.base.viewmodel.ToolbarViewModel
import com.betbull.echo.main.model.Api
import com.betbull.echo.main.model.ResponseObject
import com.betbull.echo.main.model.SocketRepository
import com.betbull.echo.main.view.ListItemViewHolder
import com.betbull.echo.main.viewmodel.ListItemViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


const val LOGIN_MESSAGE = "LOGIN"
const val LOGOUT_MESSAGE = "LOGOUT"

class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var api: Api

    private val repository = SocketRepository() // FIXME use dagger

    val list = MutableLiveData<MutableList<ListItemViewHolder>>()
    val progressBarVisible = MutableLiveData<Boolean>().apply { value = true }
    val editTextField: ObservableField<String> = ObservableField()

    override val toolbarViewModel: ToolbarViewModel? = ToolbarViewModel()

    init {
        fetchItemList()
        subscribeToSocket()
    }

    fun onClickSendButton() {
        repository.sendMessage(editTextField.get().orEmpty())
    }

    private fun subscribeToSocket() {
        repository.messageObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                handleSocketMessage(it)
            }.autoDispose()
    }

    private fun handleSocketMessage(text: String?) {

        when (text) {
            LOGIN_MESSAGE -> {
                loginUser()
            }
            LOGOUT_MESSAGE -> {
                logoutUser()
            }
            else -> {
                updateListItemIfValid(text)
            }
        }
    }

    private fun updateListItemIfValid(text: String?) {
        val pair = convertMessageToPair(text)

        if (pair == null) {
            //FIXME show invalid input & socket error
            return
        }

        list.value?.find { it.viewModel.item.id == pair.first }?.viewModel?.item?.name = pair.second

        list.value = list.value // FIXME find an elegant solution

    }

    private fun logoutUser() {
        toolbarViewModel?.title?.value = ResourceUtil.getString(R.string.app_name)
    }

    private fun loginUser() {
        toolbarViewModel?.title?.value = ResourceUtil.getString(R.string.welcome_message)
    }

    private fun convertMessageToPair(text: String?): Pair<Int, String>? {
        // FIXME refactor & move to usecase/interactor class

        if (!text?.contains(MESSAGE_SEPARATOR).orFalse()) {
            return null
        }

        val id = text?.substring(0, text.indexOf(MESSAGE_SEPARATOR))

        val idInt = id?.toIntOrNull() ?: return null

        val name = text.substring(text.indexOf(MESSAGE_SEPARATOR) + 1)

        return if (name.isNotEmpty()) {
            Pair(idInt, name)
        } else {
            null
        }

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
            ListItemViewModel(it)
        }?.mapTo(list) { ListItemViewHolder(it) }

        this.list.value = list

        progressBarVisible.value = false
    }

    private fun handleError() {
        progressBarVisible.value = false

        //FIXME handle Error
    }

}
