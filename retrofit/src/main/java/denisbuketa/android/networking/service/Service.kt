package denisbuketa.android.networking.service

import denisbuketa.android.networking.model.PopularMovies
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    // /3/movie/popular?api_key={apikey}&language={language}&page={page}&region={region}
    @GET(POPULAR_MOVIES)
    fun popularMoviesRaw(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String
    ): Call<ResponseBody>

    @GET(POPULAR_MOVIES)
    fun popularMovies(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String
    ): Call<PopularMovies>

    @GET(POPULAR_MOVIES)
    fun popularMoviesObservable(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String
    ): Observable<PopularMovies>
}