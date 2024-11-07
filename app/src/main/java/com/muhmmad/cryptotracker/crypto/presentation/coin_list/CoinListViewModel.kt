package com.muhmmad.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhmmad.cryptotracker.core.domain.util.onError
import com.muhmmad.cryptotracker.core.domain.util.onSuccess
import com.muhmmad.cryptotracker.crypto.domain.Coin
import com.muhmmad.cryptotracker.crypto.domain.CoinDataSource
import com.muhmmad.cryptotracker.crypto.presentation.coin_details.DataPoint
import com.muhmmad.cryptotracker.crypto.presentation.models.CoinUi
import com.muhmmad.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(private val coinDataSource: CoinDataSource) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state.onStart {
        loadCoins()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), CoinListState())

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            CoinListAction.OnRefresh -> loadCoins()
            is CoinListAction.OnCoinClick -> selectCoin(action.coin)
        }
    }

    private fun selectCoin(coin: CoinUi) {
        _state.update { it.copy(selectedCoin = coin) }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coin.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess { history ->
                val dataPoints = history.sortedBy { it.dateTime }.map {
                    DataPoint(
                        x = it.dateTime.hour.toFloat(),
                        y = it.priceUsd.toFloat(),
                        xLabel = DateTimeFormatter.ofPattern("ha\nM/d").format(it.dateTime)
                    )
                }

                _state.update { it.copy(selectedCoin = it.selectedCoin?.copy(coinPriceHistory = dataPoints)) }

            }.onError {
                _events.send(CoinListEvent.Error(it))
            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            coinDataSource.getCoins().onSuccess { coins ->
                _state.update {
                    it.copy(isLoading = false,
                        coins = coins.map { it.toCoinUi() })
                }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }
                _events.send(CoinListEvent.Error(error))
            }
        }
    }
}