package kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_details_equipment")
data class EquipmentEntity(
    @PrimaryKey
    val id: Int,
    val image: String?,
    val localizedName: String?,
    val name: String?
)
