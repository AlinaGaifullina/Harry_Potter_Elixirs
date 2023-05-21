package ru.itis.harrypotterelixirs.domain.usecases

import ru.itis.harrypotterelixirs.domain.entity.mapElixirEntity
import ru.itis.harrypotterelixirs.domain.repositories.ElixirsRepository
import ru.itis.harrypotterelixirs.ui.model.ElixirDataModel

class GetElixirByName(private val elixirsRepository: ElixirsRepository) {

    suspend operator fun invoke(name: String) : List<ElixirDataModel> {
        return (elixirsRepository.getElixirByName(name)).map { it.mapElixirEntity() }
    }
}