package ru.itis.harrypotterelixirs.domain.usecases

import ru.itis.harrypotterelixirs.domain.entity.mapElixirEntity
import ru.itis.harrypotterelixirs.domain.repositories.ElixirsRepository
import ru.itis.harrypotterelixirs.ui.model.ElixirDataModel

class GetAllElixirs (private val elixirsRepository: ElixirsRepository) {

    suspend operator fun invoke() : List<ElixirDataModel> {
        return (elixirsRepository.getAllElixirs()).map { it.mapElixirEntity() }
    }
}