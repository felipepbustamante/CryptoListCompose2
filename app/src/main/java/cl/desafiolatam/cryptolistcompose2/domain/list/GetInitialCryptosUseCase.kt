package cl.desafiolatam.cryptolistcompose2.domain.list

import cl.desafiolatam.cryptolistcompose2.domain.Crypto
import cl.desafiolatam.cryptolistcompose2.model.CryptoRepository

class GetInitialCryptosUseCase {

    private val repository = CryptoRepository()

    suspend operator fun invoke(): List<Crypto> {
        repository.loadCryptos()
        return repository.getLocalCryptos().map{ Crypto(it.id, it.name, it.symbol, it.priceUsd, it.changePercent24Hr, it.supply, it.marketCapUsd, it.maxSupply)}
    }

}