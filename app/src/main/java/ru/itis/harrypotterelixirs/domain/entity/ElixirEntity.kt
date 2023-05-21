package ru.itis.harrypotterelixirs.domain.entity

import ru.itis.harrypotterelixirs.ui.model.ElixirDataModel

data class ElixirEntity(
    val elixirName: String,
    val elixirEffect: String,
    val elixirSideEffects: String,
    val elixirCharacteristics: String,
    val elixirDifficulty: String,
    val elixirIngredients: List<IngredientEntity>,
    val elixirInventors: List<InventorEntity>,
    val elixirManufacturer: String,
)

fun ElixirEntity.mapElixirEntity(): ElixirDataModel {
    return ElixirDataModel(
        elixirName = this.elixirName,
        elixirEffect = this.elixirEffect,
        elixirSideEffects = this.elixirSideEffects,
        elixirCharacteristics = this.elixirCharacteristics,
        elixirDifficulty = this.elixirDifficulty,
        elixirIngredients = this.elixirIngredients.map { it.mapIngredientEntity() },
        elixirInventors = this.elixirInventors.map{ it.mapInventorEntity() },
        elixirManufacturer = this.elixirManufacturer
    )
}