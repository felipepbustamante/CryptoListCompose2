package cl.desafiolatam.cryptolistcompose2.domain.detail

import cl.desafiolatam.cryptolistcompose2.domain.Crypto
import cl.desafiolatam.cryptolistcompose2.model.CryptoRepository

class GetInitialCryptoUseCase {

    private val repository = CryptoRepository()

    suspend operator fun invoke(id: String): Crypto? {
        repository.loadCrypto(id)
        val localCrypto = repository.getLocalCrypto(id)

        return if (localCrypto != null) {
            Crypto(localCrypto.id,
                localCrypto.name,
                localCrypto.symbol,
                localCrypto.priceUsd,
                localCrypto.changePercent24Hr,
                localCrypto.supply,
                localCrypto.marketCapUsd,
                localCrypto.maxSupply,
                localCrypto.timestamp)
        } else {
            null
        }
    }

}