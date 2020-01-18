package denisbuketa.android.networking.model

import com.google.gson.annotations.SerializedName

data class PopularMovies(
    @SerializedName("page") val page: Int = -1,
    @SerializedName("total_results") val totalResults: Int = -1,
    @SerializedName("total_pages") val totalPages: Int = -1,
    @SerializedName("results") val results: List<ApiMovie> = emptyList()
)