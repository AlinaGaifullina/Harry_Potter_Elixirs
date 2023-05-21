package ru.itis.harrypotterelixirs.data.mapper

import ru.itis.harrypotterelixirs.data.model.response.InventorInfo
import ru.itis.harrypotterelixirs.domain.entity.InventorEntity
import javax.inject.Inject

class InventorInfoMapper @Inject constructor() {
    fun map(item: InventorInfo?): InventorEntity {
        return item?.let { response ->
            with(response) {
                InventorEntity(
                    inventorName = inventorName ?: ""
                )
            }
        } ?: InventorEntity(
            inventorName = ""
        )
    }
}