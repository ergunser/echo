package com.betbull.echo.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.betbull.echo.R
import com.betbull.echo.base.ui.BaseActivity
import com.betbull.echo.databinding.ActivityMainBinding
import com.betbull.echo.main.MainViewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    override fun showError(errorMessage: String?) {
        super.showError(errorMessage)
        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
