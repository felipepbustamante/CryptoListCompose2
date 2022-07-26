package cl.desafiolatam.cryptolistcompose2.domain

data class Crypto(
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String,
    val marketCapUsd: String,
    val maxSupply: String?
)