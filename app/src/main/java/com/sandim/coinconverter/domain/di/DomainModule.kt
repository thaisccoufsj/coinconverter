package com.sandim.coinconverter.domain.di

import com.sandim.coinconverter.domain.GetExchangeValueUseCase
import com.sandim.coinconverter.domain.ListExchangeUseCase
import com.sandim.coinconverter.domain.SaveExchangeusesCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {
    fun load() {
        loadKoinModules(useCaseModulos())
    }

    private fun useCaseModulos(): Module {
        return module {
            factory { ListExchangeUseCase(get()) }
            factory { SaveExchangeusesCase(get()) }
            factory { GetExchangeValueUseCase(get()) }
        }
    }
}