package com.sandim.coinconverter.domain

import com.sandim.coinconverter.core.UseCase
import com.sandim.coinconverter.data.model.ExchangeResponseValue
import com.sandim.coinconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class ListExchangeUseCase(
    private val repository: CoinRepository
) : UseCase.NoParam<List<ExchangeResponseValue>>() {
    override suspend fun execute(): Flow<List<ExchangeResponseValue>> {
        return repository.list()
    }
}