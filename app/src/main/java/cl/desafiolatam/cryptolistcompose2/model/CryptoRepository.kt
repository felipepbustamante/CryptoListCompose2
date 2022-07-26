package cl.desafiolatam.cryptolistcompose2.model

import android.util.Log
import cl.desafiolatam.cryptolistcompose2.CryptoApplication
import cl.desafiolatam.cryptolistcompose2.model.local.CryptosDB
import cl.desafiolatam.cryptolistcompose2.model.local.LocalCrypto
import cl.desafiolatam.cryptolistcompose2.model.remote.CryptoApi
import cl.desafiolatam.cryptolistcompose2.model.remote.CryptoData
import cl.desafiolatam.cryptolistcompose2.model.remote.CryptoDetailData
import cl.desafiolatam.cryptolistcompose2.model.remote.RemoteCrypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

class CryptoRepository {

    private val restInterface = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.coincap.io")
        .build()
        .create(CryptoApi::class.java)

    private val cryptosDao = CryptosDB.getDaoInstance(CryptoApplication.getAppContext())

    suspend fun getLocalCrypto(id: String): LocalCrypto? {
        return withContext(Dispatchers.IO){
            return@withContext cryptosDao.getCrypto(id)
        }
    }

    suspend fun getLocalCryptos(): List<LocalCrypto> {
        val dao = CryptosDB.getDaoInstance(CryptoApplication.getAppContext())
        return dao.getCryptos()
    }

    suspend fun loadCryptos(){
        try{
            refreshListCache()
        } catch(e: Exception){
            when(e){
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                    if(cryptosDao.getCryptos().isEmpty()){
                        throw Exception("We cannot get the resources from network and there is no cache.")
                    }
                }
                else -> throw e
            }
        }
    }

    private suspend fun getRemoteCryptos(): CryptoData {
        return withContext(Dispatchers.IO){
            return@withContext restInterface.getCryptos()
        }
    }

    private suspend fun refreshListCache(){
        val remoteCryptos = getRemoteCryptos()
        cryptosDao.addAll(remoteCryptos.data.map{
            LocalCrypto(it.id, it.name, it.symbol, it.priceUsd, it.changePercent24Hr, it.supply, it.marketCapUsd, it.maxSupply, remoteCryptos.timestamp)
        })
    }

    suspend fun loadCrypto(id: String) {
        try {
            val remoteCrypto = getRemoteCrypto(id)
            with(remoteCrypto){
                cryptosDao.add(LocalCrypto(data.id,
                    data.name, data.symbol,
                    data.priceUsd, data.changePercent24Hr,
                    data.supply, data.marketCapUsd, data.maxSupply, timestamp))
            }

        } catch (e: Exception) {
            when(e){
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                    if(cryptosDao.getCrypto(id) == null){
                        throw Exception("We cannot get the resources from network and there is no cache.")
                    }
                }
                else -> throw e
            }
        }
    }

    private suspend fun getRemoteCrypto(id: String): CryptoDetailData {
        return withContext(Dispatchers.IO){
            Log.d("sdraf", "getting : ${restInterface.getCrypto(id)}")
            return@withContext restInterface.getCrypto(id)
        }
    }


}

fun Long.toDate(): String{
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
    val time  = this
    return simpleDateFormat.format(time).toString()
}