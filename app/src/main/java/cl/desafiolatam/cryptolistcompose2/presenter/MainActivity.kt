package cl.desafiolatam.cryptolistcompose2.presenter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.desafiolatam.cryptolistcompose2.presenter.detail.CryptoDetailScreen
import cl.desafiolatam.cryptolistcompose2.presenter.detail.CryptoDetailViewModel
import cl.desafiolatam.cryptolistcompose2.presenter.list.CryptosScreen
import cl.desafiolatam.cryptolistcompose2.presenter.list.CryptosViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoApp()
        }
    }

    @Composable
    private fun CryptoApp(){
        val navController = rememberNavController()
        NavHost(navController, startDestination = "cryptos"){
            composable(route = "cryptos"){
                val viewModel: CryptosViewModel = viewModel()
                CryptosScreen(
                    state = viewModel.state.value,
                    onCryptoClick = { id -> navController.navigate("cryptos/$id")})
            }

            composable(route = "cryptos/{crypto_id}",
            arguments = listOf(navArgument("crypto_id") {
                type = NavType.StringType
            })){
                val viewModel: CryptoDetailViewModel= viewModel()
                CryptoDetailScreen(state = viewModel.state.value)
            }
        }


    }


}

