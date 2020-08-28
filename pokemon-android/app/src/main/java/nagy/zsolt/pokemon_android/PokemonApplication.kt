package nagy.zsolt.pokemon_android

import android.app.Application
import nagy.zsolt.pokemon_android.di.AppModule
import nagy.zsolt.pokemon_android.di.VMModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PokemonApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokemonApplication)
            modules(listOf(VMModule, AppModule))
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}