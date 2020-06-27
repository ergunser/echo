package com.betbull.echo.base.ui

import com.betbull.echo.base.viewmodel.RecyclerItemViewModel

interface ViewModelLayoutHolder {

    val viewModel: RecyclerItemViewModel

    val layoutResId: Int
}

