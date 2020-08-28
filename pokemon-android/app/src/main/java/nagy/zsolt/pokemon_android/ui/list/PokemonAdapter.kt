package nagy.zsolt.pokemon_android.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nagy.zsolt.pokemon_android.R
import nagy.zsolt.pokemon_android.data.Pokemon

class PokemonAdapter(
    val items: List<Pokemon>,
    val pokemonChosen: (Pokemon) -> Unit,
    val endReached: () -> Unit
): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.row_pokemon, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = items[position]
        holder.tvName.text = pokemon.name
        holder.btnChoose.setOnClickListener { pokemonChosen(pokemon) }
        if (position == itemCount - 1) endReached()
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        val btnChoose = itemView.findViewById<TextView>(R.id.btn_choose)
    }
}