package com.example.myfilimoapp.models.home

import com.google.gson.annotations.SerializedName

data class ResponseMoviesList(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("metadata")
    val metadata: Metadata
)
{
    data class Data(
        @SerializedName("country")
        val country: String?,
        @SerializedName("genres")
        val genres: List<String?>?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("images")
        val images: List<String?>?,
        @SerializedName("imdb_rating")
        val imdb_rating: String?,
        @SerializedName("poster")
        val poster: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("year")
        val year: String?
    )

}