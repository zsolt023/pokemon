package nagy.zsolt.pokemon_android.network.model

object PokemonInfoModel {
    data class Result(val height: Int, val weight: Int, val abilities: List<Ability>?, val sprites: Sprites)
    data class Ability(val ability: AbilityInfo, val is_hidden: Boolean)
    data class AbilityInfo(val name: String)

    data class Sprites(val front_default: String)
}