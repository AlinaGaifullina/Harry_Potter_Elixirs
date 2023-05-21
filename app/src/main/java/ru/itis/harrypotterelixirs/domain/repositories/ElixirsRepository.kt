package ru.itis.harrypotterelixirs.domain.repositories

import ru.itis.harrypotterelixirs.domain.entity.ElixirEntity

interface ElixirsRepository {
    suspend fun getElixirByName(name: String) : List<ElixirEntity>

    suspend fun getAllElixirs() : List<ElixirEntity>
}