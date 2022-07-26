package cl.desafiolatam.cryptolistcompose2.presenter.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.desafiolatam.cryptolistcompose2.domain.detail.GetInitialCryptoUseCase
import kotlinx.coroutines.launch

class CryptoDetailViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = mutableStateOf(CryptoDetailState(crypto = null, isLoading = true))

    val state: State<CryptoDetailState>
        get() = _state

    val id = savedStateHandle.get<String>("crypto_id") ?: ""

    val getInitialCryptoUseCase = GetInitialCryptoUseCase()

    init{
        getCrypto()
    }

     private fun getCrypto(){
        viewModelScope.launch {
            val crypto = getInitialCryptoUseCase(id)
            _state.value = CryptoDetailState(crypto, false)
        }
    }
}