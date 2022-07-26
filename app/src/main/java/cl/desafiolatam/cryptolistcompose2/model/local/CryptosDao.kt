package cl.desafiolatam.cryptolistcompose2.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.desafiolatam.cryptolistcompose2.domain.Crypto
import cl.desafiolatam.cryptolistcompose2.model.remote.RemoteCrypto

@Dao
interface CryptosDao {
    @Query("select * from cryptos")
    suspend fun getCryptos(): List<LocalCrypto>

    @Query("select * from cryptos where id = (:id)")
    suspend fun getCrypto(id: String): LocalCrypto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(list: List<LocalCrypto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(remoteCrypto: LocalCrypto)


}