package kz.spoonacular.data.mapper.db_mapper

import androidx.core.text.HtmlCompat
import kz.spoonacular.data.model.db_models.detailed_entities.RecipeDetailsEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.AnalyzedInstructionEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.EquipmentEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.IngredientEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.StepEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.ExtendedIngredientEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.MeasuresEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.MetricEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.UsEntity
import kz.spoonacular.data.model.db_models.detailed_entities.winePairing.ProductMatcheEntity
import kz.spoonacular.data.model.db_models.detailed_entities.winePairing.WinePairingEntity
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
abstract class RecipeDetailedMapperDB(
    private val wineMapper: WineMapper,
    private val analyzedMapper: AnalyzedInstrMapper,
    private val extendedIngrMapper: ExtendedIngrMapper
): Mapper<RecipeDetailsEntity, RecipeDetailed> {
    override fun map(model: RecipeDetailsEntity): RecipeDetailed {
        return RecipeDetailed(
            analyzedInstructions = model.analyzedInstructionsIds?.map { id ->
                analyzedMapper.map(getAnalyzedById(id))
            } ?: emptyList(),
            cheap = model.cheap ?: false,
            cookingMinutes = model.cookingMinutes ?: -1,
            creditsText = model.creditsText ?: "",
            cuisines = model.cuisines ?: emptyList(),
            dairyFree = model.dairyFree ?: false,
            diets = model.diets ?: emptyList(),
            dishTypes = model.dishTypes ?: emptyList(),
            extendedIngredients = model.extendedIngredientsIds?.let { list ->
                list.map { id ->
                    extendedIngrMapper.map(getExtendedById(id))
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
            winePairing = model.winePairingId?.let { wineMapper.map(getWineParingById(it)) } ?: WinePairing(
                emptyList(), "", emptyList())
        )
    }

    abstract fun getWineParingById(id: Int) : WinePairingEntity

    abstract fun getAnalyzedById(id: Int) : AnalyzedInstructionEntity

    abstract  fun getExtendedById(id: Int) : ExtendedIngredientEntity
}

abstract class WineMapper: Mapper<WinePairingEntity, WinePairing> {
    override fun map(model: WinePairingEntity): WinePairing {
        val productMatcheMapper = ProductMatchesMapper()
        return WinePairing(
            pairedWines = model.pairedWines ?: emptyList(),
            pairingText = model.pairingText ?: "",
            productMatches = model.productMatchesIds?.map { id ->
                productMatcheMapper.map(getProductMatcheById(id))
            } ?: emptyList()
        )
    }

    abstract fun getProductMatcheById(id: Int): ProductMatcheEntity

    inner class ProductMatchesMapper: Mapper<ProductMatcheEntity, ProductMatche> {
        override fun map(model: ProductMatcheEntity): ProductMatche = ProductMatche(
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

abstract class AnalyzedInstrMapper(
    private val stepsMapper: StepsMapper
): Mapper<AnalyzedInstructionEntity, AnalyzedInstruction> {
    override fun map(model: AnalyzedInstructionEntity): AnalyzedInstruction {
        return AnalyzedInstruction(
            name = model.name ?: "",
            steps = model.stepsIds?.map { id ->
                stepsMapper.map(getStepById(id))
            } ?: emptyList()
        )
    }

    abstract fun getStepById(id: Int) : StepEntity

    abstract class StepsMapper: Mapper<StepEntity, Step> {
        private val ingredientsMapper = IngredientsMapper()

        private val equipmentsMapper = EquipmentsMapper()

        override fun map(model: StepEntity): Step = Step(
                ingredients = model.ingredientsId?.map { id ->
                    ingredientsMapper.map(getIngredientsById(id))
                } ?: emptyList(),
                equipment = model.equipmentId?.map { id ->
                    equipmentsMapper.map(getEquipmentsById(id))
                } ?: emptyList(),
                number = model.number ?: -1,
                step = model.step ?: ""
            )

        abstract fun getIngredientsById(id: Int): IngredientEntity

        abstract fun getEquipmentsById(id: Int): EquipmentEntity

        inner class IngredientsMapper: Mapper<IngredientEntity, Ingredient> {
            override fun map(model: IngredientEntity): Ingredient = Ingredient(
                id = model.id ?: -1,
                image = model.image ?: "",
                localizedName = model.localizedName ?: "",
                name = model.name ?: ""
            )
        }

        inner class EquipmentsMapper: Mapper<EquipmentEntity, Equipment> {
            override fun map(model: EquipmentEntity): Equipment = Equipment(
                id = model.id ?: -1,
                image = model.image ?: "",
                localizedName = model.localizedName ?: "",
                name = model.name ?: ""
            )
        }

    }

}


abstract class ExtendedIngrMapper(
    private val measuresMapper: MeasuresMapper
): Mapper<ExtendedIngredientEntity, ExtendedIngredient> {
    override fun map(model: ExtendedIngredientEntity): ExtendedIngredient {
        return ExtendedIngredient(
            aisle = model.aisle ?: "",
            amount = model.amount ?: (-1).toDouble(),
            consistency = model.consistency ?: "",
            id = model.id ?: -1,
            image = model.image ?: "",
            measures = model.measuresId?.let { id ->
                measuresMapper.map(getMeasureById(id))
            } ?: Measures(
                us = Us((-1).toDouble(), "", ""),
                metric = Metric((-1).toDouble(), "", "")
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

    abstract fun getMeasureById(id: Int) : MeasuresEntity

    abstract class MeasuresMapper: Mapper<MeasuresEntity, Measures> {
        override fun map(model: MeasuresEntity): Measures {
            return Measures(
                metric = model.metricId?.let { id ->
                    MetricMapper().map(getMetricById(id))
                } ?: Metric((-1).toDouble(), "", ""),
                us = model.usId?.let { id ->
                    UsMapper().map(getUsBuId(id))
                } ?: Us((-1).toDouble(), "", "")
            )
        }

        abstract fun getMetricById(id: Int): MetricEntity

        abstract fun getUsBuId(id: Int): UsEntity

        inner class MetricMapper: Mapper<MetricEntity, Metric> {
            override fun map(model: MetricEntity): Metric = Metric(
                amount = model.amount ?: (-1).toDouble(),
                unitLong = model.unitLong ?: "",
                unitShort = model.unitShort ?: "")
        }
        inner class UsMapper: Mapper<UsEntity, Us> {
            override fun map(model: UsEntity): Us = Us(
                amount = model.amount ?: (-1).toDouble(),
                unitLong = model.unitLong ?: "",
                unitShort = model.unitShort ?: "")
        }
    }
}