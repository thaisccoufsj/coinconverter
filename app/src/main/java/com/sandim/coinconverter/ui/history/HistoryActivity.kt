package com.sandim.coinconverter.ui.history

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.sandim.coinconverter.R
import com.sandim.coinconverter.core.extensions.createDialog
import com.sandim.coinconverter.core.extensions.createProgressDialog
import com.sandim.coinconverter.databinding.ActivityHistoryBinding
import com.sandim.coinconverter.presentation.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryActivity : AppCompatActivity() {

    private val adapter by lazy { HistoryAdapter() }
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<HistoryViewModel>()
    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvHistory.adapter = adapter
        binding.rvHistory.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        bindObserve()

        lifecycle.addObserver(viewModel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun bindObserve() {
        viewModel.state.observe(this) {
            when (it) {
                HistoryViewModel.State.Loading -> dialog.show()
                is HistoryViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is HistoryViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }
}