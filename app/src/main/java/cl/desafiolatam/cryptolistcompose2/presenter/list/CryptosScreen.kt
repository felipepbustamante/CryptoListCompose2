package cl.desafiolatam.cryptolistcompose2.presenter.list

import android.widget.EditText
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.desafiolatam.cryptolistcompose2.domain.Crypto
import coil.compose.AsyncImage

@Composable
fun CryptosScreen(state: CryptosScreenState, onCryptoClick: (id: String) -> Unit) {

    val initialList = state.list
    var filteredList = state.list

    Column{
        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            filter,
            label = { Text("Filtrar")},
            onValueChange = { text -> filter = text}
        )
        LazyColumn(){
            items(filteredList){ crypto ->
                CryptoItem(crypto, onCryptoClick = { id -> onCryptoClick(id)})
            }
        }
    }

    Column(verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally){
        if(state.isLoading){
            CircularProgressIndicator()
        }
    }
}



@Preview (showBackground = true)
@Composable
fun ScreenPrev(){
    val fakeItem = Crypto("1", "BTC",
        "BTC", "12312.1235",
        "1.0", "idk", "idk,", "idk", 91596519874)
    val fakeList = listOf(fakeItem, fakeItem, fakeItem, fakeItem)
    CryptosScreen(state = CryptosScreenState(fakeList, false), onCryptoClick ={} )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoItem(crypto: Crypto, onCryptoClick: (id: String) -> Unit){

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .height(70.dp)
        .clickable { onCryptoClick(crypto.id) },
        border = BorderStroke(2.dp, if(crypto.changePercent24Hr[0] == '-') Color.Red else Color.Green)
    ){
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically ){
            AsyncImage(
                modifier = Modifier
                    .weight(0.2f)
                    .padding(top = 8.dp, bottom = 8.dp),
                model = "https://static.coincap.io/assets/icons/${crypto.symbol.lowercase()}@2x.png",
                contentDescription = null)
            Text(crypto.symbol, modifier = Modifier.weight(0.15f))
            Text("USD$ ${crypto.priceUsd.takeDecimals(2)}",
                modifier = Modifier
                    .weight(0.65f)
                    .padding(end = 10.dp), textAlign = TextAlign.End)
        }
    }

}

@Preview
@Composable
fun Prev(){
    CryptoItem(
        Crypto("1", "BTC",
            "BTC", "12312.1235",
            "1.0", "idk", "idk,", "idk", 91596519874)){}
}

fun String.takeDecimals(n: Int): String{
    val countUntilDot = this.indexOf('.')
    return this.substring(0..(countUntilDot+n))
}