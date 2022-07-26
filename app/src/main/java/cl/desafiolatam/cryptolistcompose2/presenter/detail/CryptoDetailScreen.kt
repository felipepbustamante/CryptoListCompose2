package cl.desafiolatam.cryptolistcompose2.presenter.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.desafiolatam.cryptolistcompose2.domain.Crypto
import cl.desafiolatam.cryptolistcompose2.model.toDate
import cl.desafiolatam.cryptolistcompose2.presenter.list.takeDecimals
import coil.compose.AsyncImage

@Composable
fun CryptoDetailScreen(
    state: CryptoDetailState
) {

    if (state.crypto != null){
        Box(){
            if(state.isLoading){
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    CircularProgressIndicator()
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()){
                Log.d("asdfas", "starting column: ${state.crypto}")
                Column(
                    modifier = Modifier
                        .weight(0.35f)
                        .background(color = Color(80, 101, 255, 60)),
                    horizontalAlignment = Alignment.CenterHorizontally){

                    AsyncImage(
                        model ="https://static.coincap.io/assets/icons/${state.crypto.symbol.lowercase()}@2x.png",
                        contentDescription = null,
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(top = 15.dp, bottom = 10.dp)
                            .fillMaxWidth())

                    Text(state.crypto.symbol, modifier = Modifier
                        .weight(0.25f)
                        .padding(bottom = 10.dp), fontSize = 30.sp)
                    Text(state.crypto.timestamp.toDate(), modifier = Modifier
                        .weight(0.25f))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.15f)){
                        Text(state.crypto.name, modifier = Modifier
                            .weight(0.2f)
                            .padding(bottom = 10.dp, start = 10.dp))
                        Text("USD$ ${state.crypto.priceUsd.takeDecimals(4)}", modifier = Modifier
                            .weight(0.2f)
                            .padding(bottom = 10.dp, end = 10.dp), textAlign = TextAlign.End)
                    }
                }

                Column(
                    modifier = Modifier.weight(0.65f).background(Color(0,0,0,18))
                ){
                    Text("Dif (24h): ${state.crypto.changePercent24Hr.takeDecimals(2)}%",
                        modifier = Modifier
                        .padding(top = 18.dp, start = 10.dp)
                        .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp,
                        color = if(state.crypto.changePercent24Hr.first() == '-') Color.Red else Color(
                            76,
                            175,
                            80,
                            255
                        )
                    )

                    Text("marketCap: ${state.crypto.marketCapUsd.takeDecimals(2)}",
                        modifier = Modifier
                            .padding(top = 18.dp, start = 10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp)
                }


            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun Prev(){
    CryptoDetailScreen(state = CryptoDetailState(
        Crypto(id="cardano", name="Cardano", symbol="ADA", priceUsd="0.4940375441169096",
            changePercent24Hr="-4.5957906512680912", supply="33752565071.2880000000000000",
            marketCapUsd="16675034355.4653073181302516", maxSupply="45000000000.0000000000000000", 1394875063249), false))
}