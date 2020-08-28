package nagy.zsolt.pokemon_android.ui.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nagy.zsolt.pokemon_android.R
import nagy.zsolt.pokemon_android.data.Ability

class PokemonInfoAdapter(
    val items: List<Ability>
) : RecyclerView.Adapter<PokemonInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.row_pokemon_info, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ability = items[position]
        holder.tvAbility.text = ability.name
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAbility = itemView.findViewById<TextView>(R.id.tv_ability)
    }
}