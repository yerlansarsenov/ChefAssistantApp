package kz.spoonacular.data.model.db_models.detailed_entities.winePairing

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recipe_details_winepairing")
data class WinePairingEntity(
    val pairedWines: List<String>?,
    @PrimaryKey
    val pairingText: String?,
    val productMatchesIds: List<Int>?
)