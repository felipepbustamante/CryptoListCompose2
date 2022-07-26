package cl.desafiolatam.cryptolistcompose2.model

import android.util.Log
import cl.desafiolatam.cryptolistcompose2.CryptoApplication
import cl.desafiolatam.cryptolistcompose2.model.local.CryptosDB
import cl.desafiolatam.cryptolistcompose2.model.local.LocalCrypto
import cl.desafiolatam.cryptolistcompose2.model.remote.CryptoApi
import cl.desafiolatam.cryptolistcompose2.model.remote.RemoteCrypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException

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

    private suspend fun getRemoteCryptos(): List<RemoteCrypto>{
        return withContext(Dispatchers.IO){
            return@withContext restInterface.getCryptos().data
        }
    }

    private suspend fun refreshListCache(){
        val remoteCryptos = getRemoteCryptos()
        cryptosDao.addAll(remoteCryptos.map{
            LocalCrypto(it.id, it.name, it.symbol, it.priceUsd, it.changePercent24Hr, it.supply, it.marketCapUsd, it.maxSupply)
        })
    }

    suspend fun loadCrypto(id: String) {
        try {
            val remoteCrypto = getRemoteCrypto(id)
            cryptosDao.add(LocalCrypto(remoteCrypto.id, remoteCrypto.name, remoteCrypto.symbol, remoteCrypto.priceUsd, remoteCrypto.changePercent24Hr, remoteCrypto.supply, remoteCrypto.marketCapUsd, remoteCrypto.maxSupply))
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

    private suspend fun getRemoteCrypto(id: String): RemoteCrypto {
        return withContext(Dispatchers.IO){
            Log.d("sdraf", "getting : ${restInterface.getCrypto(id)}")
            return@withContext restInterface.getCrypto(id).data
        }
    }


}