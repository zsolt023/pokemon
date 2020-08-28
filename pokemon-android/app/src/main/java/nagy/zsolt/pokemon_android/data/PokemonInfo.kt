package nagy.zsolt.pokemon_android.data

data class PokemonInfo(
    val height: Int, val weight: Int, val imageUrl: String, val abilities: List<Ability>?
)

data class Ability(val name: String, val hidden: Boolean)