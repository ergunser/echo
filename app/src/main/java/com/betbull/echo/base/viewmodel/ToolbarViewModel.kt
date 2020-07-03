package com.betbull.echo.base.viewmodel

import androidx.lifecycle.MutableLiveData
import com.betbull.echo.R
import com.betbull.echo.base.util.ResourceUtil

class ToolbarViewModel {

    var title: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = ResourceUtil.getString(R.string.app_name)
    }

}
