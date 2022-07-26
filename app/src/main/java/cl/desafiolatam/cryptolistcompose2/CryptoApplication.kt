package cl.desafiolatam.cryptolistcompose2

import android.app.Application
import android.content.Context

class CryptoApplication : Application() {
    init {
        app = this
    }

    companion object{
        private lateinit var app: CryptoApplication
        fun getAppContext(): Context = app.applicationContext
    }
}