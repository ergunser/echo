package com.betbull.echo.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.betbull.echo.main.MainViewModel

abstract class BaseActivity : AppCompatActivity() {

    open lateinit var viewModel: MainViewModel


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
    }

    open fun hideError() {}

    open fun showError(errorMessage: String?) {}
}
