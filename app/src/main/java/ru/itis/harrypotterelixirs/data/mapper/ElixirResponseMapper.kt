package ru.itis.harrypotterelixirs.data.mapper

import ru.itis.harrypotterelixirs.data.model.response.ElixirResponse
import ru.itis.harrypotterelixirs.domain.entity.ElixirEntity
import javax.inject.Inject

class ElixirResponseMapper @Inject constructor(
    private val ingredientInfoMapper: IngredientInfoMapper,
    private val inventorInfoMapper: InventorInfoMapper
) {
    fun map(item: ElixirResponse?): ElixirEntity {
        return item?.let { response ->
            with(response) {
                ElixirEntity(
                    elixirName = elixirName ?: "",
                    elixirEffect = elixirEffect ?: "",
                    elixirSideEffects = elixirSideEffects ?: "",
                    elixirCharacteristics = elixirCharacteristics ?: "",
                    elixirDifficulty = elixirDifficulty ?: "",
                    elixirIngredients = elixirIngredients?.map { (ingredientInfoMapper::map) (it) } ?: emptyList(),
                    elixirInventors = elixirInventors?.map { (inventorInfoMapper::map) (it) } ?: emptyList(),
                    elixirManufacturer = elixirManufacturer ?: ""
                )
            }
        } ?: ElixirEntity(
            elixirName = "",
            elixirEffect = "",
            elixirSideEffects = "",
            elixirCharacteristics = "",
            elixirDifficulty = "",
            elixirIngredients = emptyList(),
            elixirInventors = emptyList(),
            elixirManufacturer = ""
        )
    }
}