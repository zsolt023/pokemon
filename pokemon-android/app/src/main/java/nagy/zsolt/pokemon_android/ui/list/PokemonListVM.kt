package nagy.zsolt.pokemon_android.ui.list

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers
import nagy.zsolt.pokemon_android.data.Pokemon
import nagy.zsolt.pokemon_android.network.Service
import timber.log.Timber

class PokemonListVM(
    private val service: Service
) : ViewModel() {

    private val pokemonListRelay = PublishRelay.create<List<Pokemon>>()
    private val networkDisp = SerialDisposable()

    /**
     * Holds the limit variable for the pokemonlist request.
     */
    private val limit = 10

    /**
     * Holds the offset variable for the pokemonlist request. Should be updated after each request.
     */
    private var offset = 0

    /**
     * This variable holds the amount of pokemons. Should be updated from each pokemonlist response.
     */
    private var pokemonCount = -1

    /**
     * This variable holds the amount of downloaded pokemons. Should be updated from each pokemonlist response.
     */
    private var downloadedPokemonCount = 0

    fun pokemonList(): Observable<List<Pokemon>> = pokemonListRelay

    fun downloadNextPage() {
        if (downloadedPokemonCount == pokemonCount) return

        service.getPokemonPaged(offset, limit)
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                pokemonCount = result.count
                downloadedPokemonCount += result.results.size
                pokemonListRelay.accept(
                    result.results.map { Pokemon(it.name) }
                )
                offset += limit
            }, Timber::e).also { networkDisp.set(it) }
    }

    override fun onCleared() {
        super.onCleared()
        networkDisp.set(null)
    }
}