package com.sandim.coinconverter.domain

import com.sandim.coinconverter.core.UseCase
import com.sandim.coinconverter.data.model.ExchangeResponseValue
import com.sandim.coinconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveExchangeusesCase(
    private val repository : CoinRepository
) : UseCase.NoSource<ExchangeResponseValue>(){
    override suspend fun execute(param: ExchangeResponseValue): Flow<Unit> {
        return flow {
            repository.save(param)
            emit(Unit)
        }
    }
}