package cl.desafiolatam.cryptolistcompose2.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptos")
data class LocalCrypto(
    @PrimaryKey()
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String,
    val marketCapUsd: String,
    val maxSupply: String?,
    val timestamp: Long) {
}