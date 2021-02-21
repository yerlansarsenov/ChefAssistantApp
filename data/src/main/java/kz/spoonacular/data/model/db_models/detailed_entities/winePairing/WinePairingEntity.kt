package kz.spoonacular.data.model.db_models.detailed_entities.winePairing

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recipe_details_winepairing")
data class WinePairing(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatchesIds: List<Int>
)