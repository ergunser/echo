package com.betbull.echo.main

import com.betbull.echo.R
import com.betbull.echo.base.ui.ViewModelLayoutHolder

class ListItemViewHolder(override val viewModel: ListItemViewModel) : ViewModelLayoutHolder {

    override val layoutResId: Int
        get() = R.layout.list_item

}
