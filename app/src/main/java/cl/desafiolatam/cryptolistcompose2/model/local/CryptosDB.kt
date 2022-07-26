package cl.desafiolatam.cryptolistcompose2.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.desafiolatam.cryptolistcompose2.domain.Crypto

@Database(entities = [LocalCrypto::class], version = 3, exportSchema = false)
abstract class CryptosDB : RoomDatabase(){

    abstract val cryptosDao: CryptosDao

    companion object{
        @Volatile
        private var INSTANCE: CryptosDao? =  null

        fun getDaoInstance(context: Context): CryptosDao{

            synchronized(this)  {
                var instance = INSTANCE
                if (instance == null){
                    instance  = buildDatabase(context).cryptosDao
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context: Context): CryptosDB =
            Room.databaseBuilder(
                context.applicationContext,
                CryptosDB::class.java,
                "cryptos_databse")
                .fallbackToDestructiveMigration()
                .build()
    }
}