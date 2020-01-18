package denisbuketa.android.networking.service

object ServiceProvider {

    val movieDbService: Service = RetrofitProvider.retrofit.create(Service::class.java)
}