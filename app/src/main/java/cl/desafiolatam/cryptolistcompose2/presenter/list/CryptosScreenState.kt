package cl.desafiolatam.cryptolistcompose2.presenter.list

import cl.desafiolatam.cryptolistcompose2.domain.Crypto

data class CryptosScreenState(val list: List<Crypto>, val isLoading: Boolean)