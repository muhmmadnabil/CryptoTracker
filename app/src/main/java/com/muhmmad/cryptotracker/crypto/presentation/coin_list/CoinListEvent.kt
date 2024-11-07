package com.muhmmad.cryptotracker.crypto.presentation.coin_list

import com.muhmmad.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}