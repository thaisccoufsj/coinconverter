package com.sandim.coinconverter.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import com.sandim.coinconverter.R
import com.sandim.coinconverter.core.extensions.*
import com.sandim.coinconverter.data.model.Coin
import com.sandim.coinconverter.databinding.ActivityMainBinding
import com.sandim.coinconverter.presentation.MainViewModel
import com.sandim.coinconverter.ui.history.HistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val dialog by lazy { createProgressDialog() }
    private val binding by lazy { ActivityMainBinding .inflate(layoutInflater)}
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        bindAdapters()
        bindListeners()
        bindingObserve()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.action_history -> startActivity(Intent(this, HistoryActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun bindingObserve() {
        viewModel.state.observe(this) {
            when(it){
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }

                MainViewModel.State.Loading -> {
                    dialog.show()
                }

                is MainViewModel.State.Sucess -> {
                    success(it)
                }

                MainViewModel.State.Saved -> {
                    dialog.dismiss()
                    createDialog{
                        setMessage("Item salvo com sucesso")
                    }.show()
                }
            }
        }
    }

    private fun success(it: MainViewModel.State.Sucess) {
        dialog.dismiss()
        binding.btnSalvar.isEnabled = true
        val result = it.exchange.bid * binding.tilValue.text.toDouble()
        val selectedCoin = binding.tilTo.text
        val coin = Coin.getByName(selectedCoin)
        binding.tvResult.text = result.formatCurrency(coin.locale)
    }

    private fun bindAdapters() {
        val coins = Coin.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, coins)

        binding.tvFrom.setAdapter(adapter)
        binding.tvTo.setAdapter(adapter)

        binding.tvFrom.setText(Coin.BRL.name, false)
        binding.tvTo.setText(Coin.USD.name, false)
    }

    private fun bindListeners() {
        binding.tilValue.editText?.doAfterTextChanged {
            binding.btnConverter.isEnabled = it != null && it.toString().isNotEmpty()
            binding.btnSalvar.isEnabled = false
        }

        binding.btnConverter.setOnClickListener {
            it.hideSoftKeyboard()
            val search = "${binding.tilFrom.text}-${binding.tilTo.text}"
            viewModel.getExchangedValue(search)
        }

        binding.btnSalvar.setOnClickListener {
            val value = viewModel.state.value
            (value as? MainViewModel.State.Sucess)?.let {
                viewModel.saveExchange(it.exchange.copy(bid = it.exchange.bid * binding.tilValue.text.toDouble()))
            }
        }
    }

}