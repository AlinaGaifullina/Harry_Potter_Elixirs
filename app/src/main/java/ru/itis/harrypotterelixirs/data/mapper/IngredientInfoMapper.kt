package ru.itis.harrypotterelixirs.data.mapper

import ru.itis.harrypotterelixirs.data.model.response.IngredientInfo
import ru.itis.harrypotterelixirs.domain.entity.IngredientEntity
import javax.inject.Inject

class IngredientInfoMapper @Inject constructor() {
    fun map(item: IngredientInfo?): IngredientEntity {
        return item?.let { response ->
            with(response) {
                IngredientEntity(
                    ingredientName = ingredientName ?: ""
                )
            }
        } ?: IngredientEntity(
            ingredientName = ""
        )
    }
}