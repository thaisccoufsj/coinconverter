package com.sandim.coinconverter.domain

import com.sandim.coinconverter.core.UseCase
import com.sandim.coinconverter.data.model.ExchangeResponse
import com.sandim.coinconverter.data.model.ExchangeResponseValue
import com.sandim.coinconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class GetExchangeValueUseCase(
    private val repository: CoinRepository
): UseCase<String, ExchangeResponseValue>() {
    override suspend fun execute(param: String): Flow<ExchangeResponseValue> {
        return repository.getExchangeValue(param)
    }
}