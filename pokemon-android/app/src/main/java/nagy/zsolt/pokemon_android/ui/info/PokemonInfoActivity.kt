package nagy.zsolt.pokemon_android.ui.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_pokemon_info.*
import nagy.zsolt.pokemon_android.R
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class PokemonInfoActivity : AppCompatActivity() {

    private val viewModel by viewModel<PokemonInfoVM>()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_info)
    }

    override fun onStart() {
        super.onStart()

        val bundle = intent.extras
        val pokemonName = bundle?.getString(INTENT_KEY_POKEMON_NAME)
            ?: throw RuntimeException("No Pokemon name provided!")
        tv_name.text = pokemonName

        viewModel.getPokemonInfo(pokemonName)
            .subscribe({
                tv_height.text = getString(R.string.height, it.height)
                tv_weight.text = getString(R.string.weight, it.weight)
                Picasso.get().load(it.imageUrl).into(iv_picture)
                rv_abilities.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                rv_abilities.adapter = PokemonInfoAdapter(
                    it.abilities?.filter { !it.hidden } ?: emptyList()
                )
            }, Timber::e).also { disposables.add(it) }
    }

    companion object {
        /**
         * This is a key for an intent variable that stores the name of the pokemon
         */
        const val INTENT_KEY_POKEMON_NAME = "pokemonName"
    }
}