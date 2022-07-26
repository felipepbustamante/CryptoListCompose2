package cl.desafiolatam.cryptolistcompose2.presenter.detail

import cl.desafiolatam.cryptolistcompose2.domain.Crypto

data class CryptoDetailState(val crypto: Crypto?, val isLoading: Boolean)