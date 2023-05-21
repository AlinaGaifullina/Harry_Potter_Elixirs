package ru.itis.harrypotterelixirs.domain.entity

import ru.itis.harrypotterelixirs.ui.model.InventorDataModel

data class InventorEntity (
    val inventorName: String
    )
fun InventorEntity.mapInventorEntity(): InventorDataModel {
    return InventorDataModel(
        inventorName = this.inventorName
    )
}