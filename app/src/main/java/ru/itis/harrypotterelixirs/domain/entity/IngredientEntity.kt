package ru.itis.harrypotterelixirs.domain.entity

import ru.itis.harrypotterelixirs.ui.model.IngredientDataModel

data class IngredientEntity (
    val ingredientName: String
        )

fun IngredientEntity.mapIngredientEntity(): IngredientDataModel {
    return IngredientDataModel(
        ingredientName = this.ingredientName
    )
}