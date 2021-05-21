package kz.spoonacular.data.mapper

import androidx.core.text.HtmlCompat
import kz.spoonacular.data.model.reciepeDetails.RecipeDetailedData
import kz.spoonacular.data.model.reciepeDetails.analyzedInstr.AnalyzedInstructionData
import kz.spoonacular.data.model.reciepeDetails.analyzedInstr.EquipmentData
import kz.spoonacular.data.model.reciepeDetails.analyzedInstr.IngredientData
import kz.spoonacular.data.model.reciepeDetails.analyzedInstr.StepData
import kz.spoonacular.data.model.reciepeDetails.extendedIngr.ExtendedIngredientData
import kz.spoonacular.data.model.reciepeDetails.extendedIngr.MeasuresData
import kz.spoonacular.data.model.reciepeDetails.extendedIngr.MetricData
import kz.spoonacular.data.model.reciepeDetails.extendedIngr.UsData
import kz.spoonacular.data.model.reciepeDetails.winePairing.ProductMatcheData
import kz.spoonacular.data.model.reciepeDetails.winePairing.WinePairingData
import kz.spoonacular.domain.mapper.Mapper
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.AnalyzedInstruction
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Equipment
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Ingredient
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Step
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.ExtendedIngredient
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.Measures
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.Metric
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.Us
import kz.spoonacular.domain.model.reciepeDetails.winePairing.ProductMatche
import kz.spoonacular.domain.model.reciepeDetails.winePairing.WinePairing

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
class RecipeDetailedMapper: Mapper<RecipeDetailedData, RecipeDetailed> {
    override fun map(model: RecipeDetailedData): RecipeDetailed {
        val analyzedMapper = AnalyzedInstrMapper()
        val extendedIngrMapper = ExtendedIngrMapper()
        return RecipeDetailed(
            analyzedInstructions = model.analyzedInstructions?.map {
                analyzedMapper.map(it)
            } ?: emptyList(),
            cheap = model.cheap ?: false,
            cookingMinutes = model.cookingMinutes ?: -1,
            creditsText = model.creditsText ?: "",
            cuisines = model.cuisines ?: emptyList(),
            dairyFree = model.dairyFree ?: false,
            diets = model.diets ?: emptyList(),
            dishTypes = model.dishTypes ?: emptyList(),
            extendedIngredients = model.extendedIngredients?.let { list ->
                list.map {
                    extendedIngrMapper.map(it)
                }
             } ?: emptyList(),
            glutenFree = model.glutenFree ?: false,
            healthScore = model.healthScore ?: -1,
            id = model.id,
            image = model.image ?: "",
            instructions = HtmlCompat.fromHtml(model.instructions ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),                          // todo html
            license = model.license ?: "",
            lowFodmap = model.lowFodmap ?: false,
            originalId = model.originalId,
            preparationMinutes = model.preparationMinutes ?: -1,
            pricePerServing = model.pricePerServing ?: (-1).toDouble(),
            readyInMinutes = model.readyInMinutes ?: -1,
            servings = model.servings ?: -1,
            sourceName = model.sourceName ?: "",
            sourceUrl = model.sourceUrl ?: "",
            spoonacularScore = model.spoonacularScore ?: -1,
            spoonacularSourceUrl = model.spoonacularSourceUrl ?: "",
            summary = HtmlCompat.fromHtml(model.summary ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),                                // todo html
            sustainable = model.sustainable ?: false,
            title = model.title ?: "",
            vegan = model.vegan ?: false,
            vegetarian = model.vegetarian ?: false,
            veryHealthy = model.veryHealthy ?: false,
            veryPopular = model.veryPopular ?: false,
            weightWatcherSmartPoints = model.weightWatcherSmartPoints ?: -1,
            winePairing = WineMapper().map(model.winePairing ?: WinePairingData(emptyList(), "", emptyList()))
        )
    }
}

class WineMapper: Mapper<WinePairingData, WinePairing> {
    override fun map(model: WinePairingData): WinePairing {
        val productMatcheMapper = ProductMatchesMapper()
        return WinePairing(
            pairedWines = model.pairedWines ?: emptyList(),
            pairingText = model.pairingText ?: "",
            productMatches = model.productMatches?.map { productMatcheMapper.map(it) } ?: emptyList()
        )
    }
    inner class ProductMatchesMapper: Mapper<ProductMatcheData, ProductMatche> {
        override fun map(model: ProductMatcheData): ProductMatche = ProductMatche(
            averageRating = model.averageRating ?: (-1).toDouble(),
            description = model.description ?: "",
            id = model.id ?: -1,
            imageUrl = model.imageUrl ?: "",
            link = model.link ?: "",
            price = model.price ?: "",
            ratingCount = model.ratingCount ?: (-1).toDouble(),
            score = model.score ?: (-1).toDouble(),
            title = model.title ?: ""
        )
    }
}

class AnalyzedInstrMapper: Mapper<AnalyzedInstructionData, AnalyzedInstruction> {
    override fun map(model: AnalyzedInstructionData): AnalyzedInstruction {
        return AnalyzedInstruction(
            name = model.name ?: "",
            steps = model.steps?.map { StepsMapper().map(it) } ?: emptyList()
        )
    }

    inner class StepsMapper: Mapper<StepData, Step> {
        override fun map(model: StepData): Step = Step(
                ingredients = model.ingredients?.map { IngredientsMapper().map(it) } ?: emptyList(),
                equipments = model.equipment?.map { EquipmentsMapper().map(it) } ?: emptyList(),
                number = model.number ?: -1,
                step = model.step ?: ""
            )
    }

    inner class IngredientsMapper: Mapper<IngredientData, Ingredient> {
        override fun map(model: IngredientData): Ingredient = Ingredient(
                id = model.id ?: -1,
                image = model.image ?: "",
                localizedName = model.localizedName ?: "",
                name = model.name ?: ""
            )
    }

    inner class EquipmentsMapper: Mapper<EquipmentData, Equipment> {
        override fun map(model: EquipmentData): Equipment = Equipment(
                id = model.id ?: -1,
                image = model.image ?: "",
                localizedName = model.localizedName ?: "",
                name = model.name ?: ""
            )
    }

}


class ExtendedIngrMapper : Mapper<ExtendedIngredientData, ExtendedIngredient> {
    override fun map(model: ExtendedIngredientData): ExtendedIngredient {
        return ExtendedIngredient(
            aisle = model.aisle ?: "",
            amount = model.amount ?: (-1).toDouble(),
            consistency = model.consistency ?: "",
            id = model.id ?: -1,
            image = model.image ?: "",
            measures = MeasuresMapper().map(model.measures ?: MeasuresData(
                us = UsData((-1).toDouble(), "", ""),
                metric = MetricData((-1).toDouble(), "", ""))
            ),
            meta = model.meta ?: emptyList(),
            metaInformation = model.metaInformation ?: emptyList(),
            name = model.name ?: "",
            original = model.original ?: "",
            originalName = model.originalName ?: "",
            originalString = model.originalString ?: "",
            unit = model.unit ?: ""
        )
    }

    inner class MeasuresMapper: Mapper<MeasuresData, Measures> {
        override fun map(model: MeasuresData): Measures {
            return Measures(
                metric = MetricMapper().map(model.metric ?: MetricData((-1).toDouble(), "", "")),
                us = UsMapper().map(model.us ?: UsData((-1).toDouble(), "", ""))
            )
        }
        inner class MetricMapper: Mapper<MetricData, Metric> {
            override fun map(model: MetricData): Metric = Metric(
                amount = model.amount ?: (-1).toDouble(),
                unitLong = model.unitLong ?: "",
                unitShort = model.unitShort ?: "")
        }
        inner class UsMapper: Mapper<UsData, Us> {
            override fun map(model: UsData): Us = Us(
                amount = model.amount ?: (-1).toDouble(),
                unitLong = model.unitLong ?: "",
                unitShort = model.unitShort ?: "")
        }
    }
}
