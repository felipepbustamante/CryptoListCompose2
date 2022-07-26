package cl.desafiolatam.cryptolistcompose2.model.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoApi {

    @GET("/v2/assets")
    suspend fun getCryptos(): CryptoData

    @GET("/v2/assets/{id}")
    suspend fun getCrypto(@Path(value = "id") id: String): CryptoDetailData

}