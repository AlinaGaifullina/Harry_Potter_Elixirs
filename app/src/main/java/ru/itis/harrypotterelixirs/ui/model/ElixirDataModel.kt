package ru.itis.harrypotterelixirs.ui.model

data class ElixirDataModel (
    val elixirName: String,
    val elixirEffect: String,
    val elixirSideEffects: String,
    val elixirCharacteristics: String,
    val elixirDifficulty: String,
    val elixirIngredients: List<IngredientDataModel>,
    val elixirInventors: List<InventorDataModel>,
    val elixirManufacturer: String,
    )