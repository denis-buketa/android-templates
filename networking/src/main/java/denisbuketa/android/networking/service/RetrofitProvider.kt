package denisbuketa.android.networking.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    // No need to specify okHttp client. It is created by default. If you have multiple retrofit
    // instances, then it would be good that they share the same client.
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}