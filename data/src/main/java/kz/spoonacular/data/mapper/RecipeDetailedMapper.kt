package kz.spoonacular.data.mapper

import android.text.Html
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
        analyzedInstructions = model.analyzedInstructions.map { analyzedMapper.map(it) },
        cheap = model.cheap,
        cookingMinutes = model.cookingMinutes,
        creditsText = model.creditsText,
        cuisines = model.cuisines,
        dairyFree = model.dairyFree,
        diets = model.diets,
        dishTypes = model.dishTypes,
        extendedIngredients = model.extendedIngredients.map { extendedIngrMapper.map(it) },
        glutenFree = model.glutenFree,
        healthScore = model.healthScore,
        id = model.id ?: 0,
        image = model.image,
        instructions = HtmlCompat.fromHtml(model.instructions, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),                          // todo html
        license = model.license,
        lowFodmap = model.lowFodmap,
        originalId = model.originalId,
        preparationMinutes = model.preparationMinutes,
        pricePerServing = model.pricePerServing,
        readyInMinutes = model.readyInMinutes,
        servings = model.servings,
        sourceName = model.sourceName,
        sourceUrl = model.sourceUrl,
        spoonacularScore = model.spoonacularScore,
        spoonacularSourceUrl = model.spoonacularSourceUrl,
        summary = HtmlCompat.fromHtml(model.summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),                                // todo html
        sustainable = model.sustainable,
        title = model.title,
        vegan = model.vegan,
        vegetarian = model.vegetarian,
        veryHealthy = model.veryHealthy,
        veryPopular = model.veryPopular,
        weightWatcherSmartPoints = model.weightWatcherSmartPoints,
        winePairing = WineMapper().map(model.winePairing)
        )
    }
}

class WineMapper: Mapper<WinePairingData, WinePairing> {
    override fun map(model: WinePairingData): WinePairing {
        val productMatcheMapper = ProductMatchesMapper()
        return WinePairing(
            pairedWines = model.pairedWines,
            pairingText = model.pairingText,
            productMatches = model.productMatches.map { productMatcheMapper.map(it) }
        )
    }
    inner class ProductMatchesMapper: Mapper<ProductMatcheData, ProductMatche> {
        override fun map(model: ProductMatcheData): ProductMatche = ProductMatche(
            averageRating = model.averageRating,
            description = model.description,
            id = model.id,
            imageUrl = model.imageUrl,
            link = model.link,
            price = model.price,
            ratingCount = model.ratingCount,
            score = model.score,
            title = model.title
        )
    }
}

class AnalyzedInstrMapper: Mapper<AnalyzedInstructionData, AnalyzedInstruction> {
    override fun map(model: AnalyzedInstructionData): AnalyzedInstruction {
        return AnalyzedInstruction(
            name = model.name,
            steps = model.steps.map { StepsMapper().map(it) }
        )
    }

    inner class StepsMapper: Mapper<StepData, Step> {
        override fun map(model: StepData): Step = Step(
                ingredients = model.ingredients.map { IngredientsMapper().map(it) },
                equipment = model.equipment.map { EquipmentsMapper().map(it) },
                number = model.number,
                step = model.step
            )
    }

    inner class IngredientsMapper: Mapper<IngredientData, Ingredient> {
        override fun map(model: IngredientData): Ingredient = Ingredient(
                id = model.id,
                image = model.image,
                localizedName = model.localizedName,
                name = model.name
            )
    }

    inner class EquipmentsMapper: Mapper<EquipmentData, Equipment> {
        override fun map(model: EquipmentData): Equipment = Equipment(
                id = model.id,
                image = model.image,
                localizedName = model.localizedName,
                name = model.name
            )
    }

}


class ExtendedIngrMapper : Mapper<ExtendedIngredientData, ExtendedIngredient> {
    override fun map(model: ExtendedIngredientData): ExtendedIngredient {
        return ExtendedIngredient(
            aisle = model.aisle,
            amount = model.amount,
            consistency = model.consistency,
            id = model.id,
            image = model.image,
            measures = MeasuresMapper().map(model.measures),
            meta = model.meta,
            metaInformation = model.metaInformation,
            name = model.name,
            original = model.original,
            originalName = model.originalName,
            originalString = model.originalString,
            unit = model.unit
        )
    }

    inner class MeasuresMapper: Mapper<MeasuresData, Measures> {
        override fun map(model: MeasuresData): Measures {
            return Measures(
                metric = MetricMapper().map(model.metric),
                us = UsMapper().map(model.us)
            )
        }
        inner class MetricMapper: Mapper<MetricData, Metric> {
            override fun map(model: MetricData): Metric = Metric(amount = model.amount, unitLong = model.unitLong, unitShort = model.unitShort)
        }
        inner class UsMapper: Mapper<UsData, Us> {
            override fun map(model: UsData): Us = Us(amount = model.amount, unitLong = model.unitLong, unitShort = model.unitShort)
        }
    }
}