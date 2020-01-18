package denisbuketa.android.networking

import denisbuketa.android.logger.Logger
import denisbuketa.android.networking.model.PopularMovies
import denisbuketa.android.networking.service.API_KEY_VALUE
import denisbuketa.android.networking.service.Language
import denisbuketa.android.networking.service.Region
import denisbuketa.android.networking.service.ServiceProvider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val EMPTY_RESPONSE_BODY = ""

object Client {

    fun fetchPopularMoviesRaw() {
        ServiceProvider.movieDbService
            .popularMoviesRaw(API_KEY_VALUE, Language.ENGLISH_US.id, 1, Region.US.id)
            .enqueue(object : Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                    Logger.log("onFailure: $throwable")
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val responseBody = response.body()?.string() ?: EMPTY_RESPONSE_BODY
                    if (response.isSuccessful) {
                        Logger.log("Response Successful: $responseBody")
                    } else {
                        Logger.log("Response Unsuccessful: $responseBody")
                    }
                }
            })
    }

    fun fetchPopularMovies() {
        ServiceProvider.movieDbService
            .popularMovies(API_KEY_VALUE, Language.ENGLISH_US.id, 1, Region.US.id)
            .enqueue(object : Callback<PopularMovies> {

                override fun onFailure(call: Call<PopularMovies>, throwable: Throwable) {
                    Logger.log("onFailure: $throwable")
                }

                override fun onResponse(
                    call: Call<PopularMovies>,
                    response: Response<PopularMovies>
                ) {
                    val popularMovies = response.body()
                    if (response.isSuccessful) {
                        Logger.log("Response Successful: ${popularMovies.toString()}")
                    } else {
                        Logger.log("Response Unsuccessful: ${popularMovies.toString()}")
                    }
                }
            })
    }
}