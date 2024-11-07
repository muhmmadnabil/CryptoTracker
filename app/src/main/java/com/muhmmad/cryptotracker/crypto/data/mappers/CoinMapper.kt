package com.muhmmad.cryptotracker.crypto.data.mappers

import com.muhmmad.cryptotracker.crypto.data.networking.dto.CoinDto
import com.muhmmad.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.muhmmad.cryptotracker.crypto.domain.Coin
import com.muhmmad.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin = Coin(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    marketCapUsd = marketCapUsd,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr
)

fun CoinPriceDto.toCoinPrice(): CoinPrice = CoinPrice(
    priceUsd = priceUsd,
    dateTime = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault())
)
