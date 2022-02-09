package com.sandim.coinconverter.data.repository

import com.google.gson.Gson
import com.sandim.coinconverter.core.exceptions.RemoteException
import com.sandim.coinconverter.data.database.AppDatabase
import com.sandim.coinconverter.data.model.ErrorResponse
import com.sandim.coinconverter.data.model.ExchangeResponseValue
import com.sandim.coinconverter.data.services.AwesomeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CoinRepositoryImplementer(
    private val service: AwesomeService,
    private val appDatabase: AppDatabase
) : CoinRepository {

    private val dao = appDatabase.exchangeDao()

    override suspend fun getExchangeValue(coins: String) = flow {
        try {
            val exchangeValue = service.exchangeValue(coins)
            val exchange = exchangeValue.values.first()
            emit(exchange)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponse.message)
        }
    }

    override suspend fun save(exchange: ExchangeResponseValue) {
        dao.save(exchange)
    }

    override fun list(): Flow<List<ExchangeResponseValue>> {
        return dao.findAll()
    }
}