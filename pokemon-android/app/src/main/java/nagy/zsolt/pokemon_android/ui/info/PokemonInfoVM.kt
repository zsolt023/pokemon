package nagy.zsolt.pokemon_android.ui.info

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nagy.zsolt.pokemon_android.data.Ability
import nagy.zsolt.pokemon_android.data.PokemonInfo
import nagy.zsolt.pokemon_android.network.Service

class PokemonInfoVM(
    private val service: Service
) : ViewModel() {

    fun getPokemonInfo(pokemonName: String) = service.getPokemonInfo(pokemonName)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map {
            val abilities = it.abilities?.map {
                Ability(
                    name = it.ability.name,
                    hidden = it.is_hidden
                )
            }
            PokemonInfo(
                height = it.height,
                weight = it.weight,
                imageUrl = it.sprites.front_default,
                abilities = abilities
            )
        }
}