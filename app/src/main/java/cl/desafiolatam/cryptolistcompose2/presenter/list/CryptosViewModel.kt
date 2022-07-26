package cl.desafiolatam.cryptolistcompose2.presenter.list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.desafiolatam.cryptolistcompose2.domain.list.GetInitialCryptosUseCase
import kotlinx.coroutines.launch

class CryptosViewModel : ViewModel(){

    private val getInitialUseCase = GetInitialCryptosUseCase()

    private val _state = mutableStateOf(CryptosScreenState(list = listOf(), isLoading = true))
    val state: State<CryptosScreenState>
        get() = _state

    init{
        getCryptos()
    }
    private fun getCryptos(){
        viewModelScope.launch {
            val list = getInitialUseCase()
            _state.value = CryptosScreenState(list, false)
            Log.d("<<<", "list: $list")
        }
    }
}