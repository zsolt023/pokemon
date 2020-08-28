package nagy.zsolt.pokemon_android.di

import android.content.pm.PackageManager
import nagy.zsolt.pokemon_android.network.Service
import nagy.zsolt.pokemon_android.ui.list.PokemonListVM
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val VMModule = module {
    viewModel { PokemonListVM(get()) }
}

val AppModule = module {
    single {
        val ai = androidContext().packageManager.getApplicationInfo(
            androidContext().packageName,
            PackageManager.GET_META_DATA
        )
        val bundle = ai.metaData
        val hostUrl = bundle.getString("HOST_URL")
            ?: throw NoSuchElementException(
                "No host url found with HOST_URL key!\n" +
                        "Provide one from the manifest as a meta-data!"
            )
        Service.create(hostUrl)
    }
}