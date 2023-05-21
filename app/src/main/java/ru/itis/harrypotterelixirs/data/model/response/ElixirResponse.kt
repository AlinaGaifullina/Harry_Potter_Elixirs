package ru.itis.harrypotterelixirs.data.model.response

import com.google.gson.annotations.SerializedName

data class ElixirResponse (
    @SerializedName("name")
    val elixirName: String?,
    @SerializedName("effect")
    val elixirEffect: String?,
    @SerializedName("sideEffects")
    val elixirSideEffects: String?,
    @SerializedName("characteristics")
    val elixirCharacteristics: String?,
    @SerializedName("difficulty")
    val elixirDifficulty: String?,
    @SerializedName("ingredients")
    val elixirIngredients: List<IngredientInfo>? = null,
    @SerializedName("inventors")
    val elixirInventors: List<InventorInfo>? = null,
    @SerializedName("manufacturer")
    val elixirManufacturer: String?,

    )

data class IngredientInfo(
    @SerializedName("name")
    val ingredientName: String?,
)

data class InventorInfo(
    @SerializedName("name")
    val inventorName: String?,
)