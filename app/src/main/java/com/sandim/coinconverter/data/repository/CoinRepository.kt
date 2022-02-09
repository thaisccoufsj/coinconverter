package com.sandim.coinconverter.data.repository

import com.sandim.coinconverter.data.model.ExchangeResponseValue
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    //Retrofit
    suspend fun getExchangeValue(coins: String): Flow<ExchangeResponseValue>

    //Room
    suspend fun save(exchange: ExchangeResponseValue)
    fun list():Flow<List<ExchangeResponseValue>>
}