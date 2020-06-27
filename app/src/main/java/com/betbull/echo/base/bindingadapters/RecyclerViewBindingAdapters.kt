package com.betbull.echo.base.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betbull.echo.base.ui.ViewModelLayoutHolder
import com.betbull.echo.base.ui.ViewModelRecyclerViewAdapter

@BindingAdapter("viewModelList")
fun viewModelList(
    recyclerView: RecyclerView,
    list: List<ViewModelLayoutHolder>?
) {

    if (recyclerView.adapter == null) {
        recyclerView.adapter =
            ViewModelRecyclerViewAdapter()
    }

    (recyclerView.adapter as ViewModelRecyclerViewAdapter).let {
        it.viewModelLayoutHolderList = list.orEmpty()
        it.notifyDataSetChanged()
    }
}
