package com.betbull.echo.main.view

import com.betbull.echo.R
import com.betbull.echo.base.ui.ViewModelLayoutHolder
import com.betbull.echo.main.viewmodel.ListItemViewModel

class ListItemViewHolder(override val viewModel: ListItemViewModel) : ViewModelLayoutHolder {

    override val layoutResId: Int
        get() = R.layout.list_item

}
