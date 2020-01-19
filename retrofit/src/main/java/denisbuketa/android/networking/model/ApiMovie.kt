package denisbuketa.android.networking.model

import com.google.gson.annotations.SerializedName

data class ApiMovie(
    @SerializedName("id") val id: Long = -1,
    @SerializedName("title") val title: String? = "",
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("poster_path") val posterPath: String? = ""
)