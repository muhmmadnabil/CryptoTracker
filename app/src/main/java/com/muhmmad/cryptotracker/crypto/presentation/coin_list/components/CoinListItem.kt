package com.muhmmad.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhmmad.cryptotracker.crypto.domain.Coin
import com.muhmmad.cryptotracker.crypto.presentation.models.CoinUi
import com.muhmmad.cryptotracker.crypto.presentation.models.toCoinUi
import com.muhmmad.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListItem(
    coin: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = coin.iconRes),
            contentDescription = coin.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(85.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )

            Text(
                text = coin.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = contentColor
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$ ${coin.priceUsd.formatted}",
                fontSize = 16.sp,
                color = contentColor,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            PriceChange(coin.changePercent24Hr)
        }
    }
}

//@PreviewLightDark
//@Composable
//fun CoinListItemPreview() {
//    CryptoTrackerTheme {
//        CoinListItem(coin.toCoinUi(), onClick = {})
//    }
//}

internal val coin = Coin(
    id = "btc-bitcoin",
    name = "Bitcoin",
    rank = 1,
    symbol = "BTC",
    marketCapUsd = 1234567890.0,
    priceUsd = 34567.89,
    changePercent24Hr = -12.34
)