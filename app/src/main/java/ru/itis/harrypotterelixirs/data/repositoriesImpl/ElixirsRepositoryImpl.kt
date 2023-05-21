package ru.itis.harrypotterelixirs.data.repositoriesImpl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itis.harrypotterelixirs.data.mapper.ElixirResponseMapper
import ru.itis.harrypotterelixirs.data.network.ElixirsApiService
import ru.itis.harrypotterelixirs.domain.entity.ElixirEntity
import ru.itis.harrypotterelixirs.domain.repositories.ElixirsRepository
import javax.inject.Inject

class ElixirsRepositoryImpl @Inject constructor(
    private val remoteSource: ElixirsApiService,
    private val elixirResponseMapper: ElixirResponseMapper
) : ElixirsRepository {

    override suspend fun getElixirByName(name: String): List<ElixirEntity> {
        return withContext(Dispatchers.IO) {
            (remoteSource.getElixirByName(elixirName = name)).map { (elixirResponseMapper::map) (it) }
        }
    }

    override suspend fun getAllElixirs(): List<ElixirEntity> {
        return withContext(Dispatchers.IO) {
            (remoteSource.getAllElixirs()).map { (elixirResponseMapper::map) (it) }
        }
    }
}