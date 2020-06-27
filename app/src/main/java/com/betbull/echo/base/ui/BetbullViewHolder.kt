package com.betbull.echo.base.ui

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.library.baseAdapters.BR

class BetbullViewHolder(
    viewGroup: ViewGroup,
    @LayoutRes layoutRes: Int
) : ViewModelViewHolder(viewGroup, layoutRes, BR.viewModel)
