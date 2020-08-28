package nagy.zsolt.pokemon_android.network.model

object PokemonListModel {
    data class Result(val count: Int, val results: List<PokemonModel>)
    data class PokemonModel(val name: String)
}