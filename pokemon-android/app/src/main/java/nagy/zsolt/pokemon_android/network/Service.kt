package nagy.zsolt.pokemon_android.network

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import nagy.zsolt.pokemon_android.network.model.PokemonInfoModel
import nagy.zsolt.pokemon_android.network.model.PokemonListModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * This is an interface for the retrofit server communication.
 */
interface Service {

    @GET("pokemon")
    fun getPokemonPaged(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<PokemonListModel.Result>

    @GET("pokemon/{name}")
    fun getPokemonInfo(
        @Path("name") pokemonName: String
    ): Observable<PokemonInfoModel.Result>

    companion object {
        /**
         * This method should be used to create a singleton instance of the [Service] class.
         *
         * @param hostUrl - the root url for the server communication
         */
        fun create(hostUrl: String): Service {
            val gb = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gb))
                .baseUrl(hostUrl)
                .build()

            return retrofit.create(Service::class.java)
        }
    }
}