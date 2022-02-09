package com.sandim.coinconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias ExchangeResponse = HashMap<String, ExchangeResponseValue>

@Entity(tableName = "exchange")
data class ExchangeResponseValue(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val code: String,
    val codein: String,
    val name: String,
    val bid: Double
)
