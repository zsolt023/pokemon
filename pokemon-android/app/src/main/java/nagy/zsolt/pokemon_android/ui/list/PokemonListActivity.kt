package nagy.zsolt.pokemon_android.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import nagy.zsolt.pokemon_android.R
import nagy.zsolt.pokemon_android.data.Pokemon
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class PokemonListActivity : AppCompatActivity() {

    private val viewModel by viewModel<PokemonListVM>()
    private val disposables = CompositeDisposable()
    private val pokemonList = mutableListOf<Pokemon>()
    private val adapter = PokemonAdapter(pokemonList, {
        // TODO: pokemon chosen, navigate to next page
    }, {
        viewModel.downloadNextPage()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)
    }

    override fun onStart() {
        super.onStart()

        rv_pokemons.layoutManager = LinearLayoutManager(this)
        rv_pokemons.adapter = adapter

        viewModel.pokemonList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newPokemons ->
                val oldPokemonCount = pokemonList.size
                pokemonList.addAll(newPokemons)
                adapter.notifyItemRangeInserted(oldPokemonCount, newPokemons.size)
            }, Timber::e).also { disposables.add(it) }

        viewModel.downloadNextPage()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}