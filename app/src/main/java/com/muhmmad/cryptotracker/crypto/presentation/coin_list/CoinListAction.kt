package com.muhmmad.cryptotracker.crypto.presentation.coin_list

import com.muhmmad.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coin: CoinUi) : CoinListAction
    data object OnRefresh : CoinListAction
}