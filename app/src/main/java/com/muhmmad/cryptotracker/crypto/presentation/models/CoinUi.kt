package com.muhmmad.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.muhmmad.cryptotracker.crypto.domain.Coin
import com.muhmmad.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.muhmmad.cryptotracker.crypto.presentation.coin_details.DataPoint
import java.text.NumberFormat
import java.util.Locale

data class CoinUi(
    val id: String,
    val name: String,
    val rank: Int,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val coinPriceHistory: List<DataPoint> = emptyList(),
    @DrawableRes val iconRes: Int
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String,
)

fun Coin.toCoinUi(): CoinUi = CoinUi(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    priceUsd = priceUsd.toDisplayNumber(),
    marketCapUsd = marketCapUsd.toDisplayNumber(),
    changePercent24Hr = changePercent24Hr.toDisplayNumber(),
    iconRes = getDrawableIdForCoin(symbol)
)

fun Double.toDisplayNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumIntegerDigits = 2
        maximumFractionDigits = 2
    }

    return DisplayableNumber(this, formatter.format(this))
}
