package ru.itis.harrypotterelixirs.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.harrypotterelixirs.data.model.response.ElixirResponse

interface ElixirsApiService {

    @GET("Elixirs")
    suspend fun getElixirByName(
        @Query("Name") elixirName: String
    ): List<ElixirResponse>

    @GET("Elixirs")
    suspend fun getAllElixirs(
    ): List<ElixirResponse>
}