package cl.desafiolatam.cryptolistcompose2.model.remote

import cl.desafiolatam.cryptolistcompose2.domain.Crypto

//{"id":"xrp",
//    "rank":"8",
//    "symbol":"XRP",
//    "name":"XRP",
//    "supply":"45404028640.0000000000000000",
//    "maxSupply":"100000000000.0000000000000000",
//    "marketCapUsd":"16316430786.4927936932251040",
//    "volumeUsd24Hr":"225279644.5645223401798858",
//    "priceUsd":"0.3593608601532411",
//    "changePercent24Hr":"0.0111349908258619",
//    "vwap24Hr":"0.3570084514717920",
//    "explorer":"https://xrpcharts.ripple.com/#/graph/"},

data class CryptoData(val data: List<RemoteCrypto>, val timeStamp: Long)

data class CryptoDetailData(val data: RemoteCrypto, val timeStamp: Long)

data class RemoteCrypto (
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String,
    val marketCapUsd: String,
    val maxSupply: String?
) {
}