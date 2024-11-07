package com.muhmmad.cryptotracker.di

import com.muhmmad.cryptotracker.core.data.networking.HttpClientFactory
import com.muhmmad.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.muhmmad.cryptotracker.crypto.domain.CoinDataSource
import com.muhmmad.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single { RemoteCoinDataSource(get()) }.bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}