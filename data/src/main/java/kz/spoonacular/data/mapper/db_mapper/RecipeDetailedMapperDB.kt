package kz.spoonacular.data.mapper.db_mapper

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
import kz.spoonacular.domain.mapper.DoubleMapper
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
    private val wineMapper: WineMapperDB,
    private val analyzedMapper: AnalyzedInstrMapperDB,
    private val extendedIngrMapper: ExtendedIngrMapperDB
): DoubleMapper<RecipeDetailsEntity, RecipeDetailed> {
    override fun mapTo(model: RecipeDetailsEntity): RecipeDetailed {
        return RecipeDetailed(
            analyzedInstructions = model.analyzedInstructionsIds?.map { id ->
                analyzedMapper.mapTo(getAnalyzedById(id))
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
                    extendedIngrMapper.mapTo(getExtendedById(id))
                }
            } ?: emptyList(),
            glutenFree = model.glutenFree ?: false,
            healthScore = model.healthScore ?: -1,
            id = model.id,
            image = model.image ?: "",
            instructions = model.instructions ?: "",
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
            summary = model.summary ?: "",
            sustainable = model.sustainable ?: false,
            title = model.title ?: "",
            vegan = model.vegan ?: false,
            vegetarian = model.vegetarian ?: false,
            veryHealthy = model.veryHealthy ?: false,
            veryPopular = model.veryPopular ?: false,
            weightWatcherSmartPoints = model.weightWatcherSmartPoints ?: -1,
            winePairing = model.winePairingId?.let { wineMapper.mapTo(getWineParingById(it)) } ?: WinePairing(
                emptyList(), "", emptyList())
        )
    }

    override fun mapFrom(model: RecipeDetailed): RecipeDetailsEntity {
        return RecipeDetailsEntity(
            analyzedInstructionsIds = model.analyzedInstructions.map {
                it.name
            },
            cheap = model.cheap,
            cookingMinutes = model.cookingMinutes,
            creditsText = model.creditsText,
            cuisines = model.cuisines,
            dairyFree = model.dairyFree,
            diets = model.diets,
            dishTypes = model.dishTypes,
            extendedIngredientsIds = model.extendedIngredients.map {
                it.id
            },
            glutenFree = model.glutenFree,
            healthScore = model.healthScore,
            id = model.id,
            image = model.image,
            instructions = model.instructions,
            license = model.license,
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
            summary = model.summary,
            sustainable = model.sustainable ?: false,
            title = model.title ?: "",
            vegan = model.vegan ?: false,
            vegetarian = model.vegetarian ?: false,
            veryHealthy = model.veryHealthy ?: false,
            veryPopular = model.veryPopular ?: false,
            weightWatcherSmartPoints = model.weightWatcherSmartPoints ?: -1,
            winePairingId = model.winePairing.pairingText
        )
    }

    abstract fun getWineParingById(name: String) : WinePairingEntity

    abstract fun getAnalyzedById(id: String) : AnalyzedInstructionEntity

    abstract  fun getExtendedById(id: Int) : ExtendedIngredientEntity
}

abstract class WineMapperDB: DoubleMapper<WinePairingEntity, WinePairing> {
    override fun mapTo(model: WinePairingEntity): WinePairing {
        val productMatcheMapper = ProductMatchesMapperDB()
        return WinePairing(
            pairedWines = model.pairedWines ?: emptyList(),
            pairingText = model.pairingText ?: "",
            productMatches = model.productMatchesIds?.map { id ->
                productMatcheMapper.mapTo(getProductMatcheById(id))
            } ?: emptyList()
        )
    }

    override fun mapFrom(model: WinePairing): WinePairingEntity {
        return WinePairingEntity(
            pairedWines = model.pairedWines ?: emptyList(),
            pairingText = model.pairingText ?: "",
            productMatchesIds = model.productMatches.map {
                it.id
            }
        )
    }

    abstract fun getProductMatcheById(id: Int): ProductMatcheEntity

    inner class ProductMatchesMapperDB: DoubleMapper<ProductMatcheEntity, ProductMatche> {
        override fun mapTo(model: ProductMatcheEntity): ProductMatche = ProductMatche(
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

        override fun mapFrom(model: ProductMatche): ProductMatcheEntity {
            return ProductMatcheEntity(
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
}

abstract class AnalyzedInstrMapperDB(
    private val stepsMapper: StepsMapperDB
): DoubleMapper<AnalyzedInstructionEntity, AnalyzedInstruction> {
    override fun mapTo(model: AnalyzedInstructionEntity): AnalyzedInstruction {
        return AnalyzedInstruction(
            name = model.name ?: "",
            steps = model.stepsIds?.map { id ->
                stepsMapper.mapTo(getStepById(id))
            } ?: emptyList()
        )
    }

    override fun mapFrom(model: AnalyzedInstruction): AnalyzedInstructionEntity {
        return AnalyzedInstructionEntity(
            name = model.name,
            stepsIds = model.steps.map {
                insertSteps(it)
            }
        )
    }

    abstract fun insertSteps(step: Step) : Int

    abstract fun getStepById(id: Int) : StepEntity

    abstract class StepsMapperDB: DoubleMapper<StepEntity, Step> {
        val ingredientsMapper = IngredientsMapperDB()

        val equipmentsMapper = EquipmentsMapperDB()

        override fun mapTo(model: StepEntity): Step = Step(
                ingredients = model.ingredientsId?.map { id ->
                    ingredientsMapper.mapTo(getIngredientsById(id))
                } ?: emptyList(),
                equipments = model.equipmentId?.map { id ->
                    equipmentsMapper.mapTo(getEquipmentsById(id))
                } ?: emptyList(),
                number = model.number ?: -1,
                step = model.step ?: ""
            )

        override fun mapFrom(model: Step): StepEntity {
            return StepEntity(
                ingredientsId = model.ingredients.map {
                    it.id
                },
                equipmentId = model.equipments.map {
                    it.id
                },
                number = model.number,
                step = model.step,
                id = 0
            )
        }

        abstract fun getIngredientsById(id: Int): IngredientEntity

        abstract fun getEquipmentsById(id: Int): EquipmentEntity

        abstract fun insertEquipment(equipment: Equipment)

        abstract fun insertIngredient(ingredient: Ingredient)

        inner class IngredientsMapperDB: DoubleMapper<IngredientEntity, Ingredient> {
            override fun mapTo(model: IngredientEntity): Ingredient = Ingredient(
                id = model.id ?: -1,
                image = model.image ?: "",
                localizedName = model.localizedName ?: "",
                name = model.name ?: ""
            )

            override fun mapFrom(model: Ingredient): IngredientEntity {
                return IngredientEntity(
                    id = model.id,
                    image = model.image,
                    localizedName = model.localizedName,
                    name = model.name
                )
            }
        }

        inner class EquipmentsMapperDB: DoubleMapper<EquipmentEntity, Equipment> {
            override fun mapTo(model: EquipmentEntity): Equipment = Equipment(
                id = model.id ?: -1,
                image = model.image ?: "",
                localizedName = model.localizedName ?: "",
                name = model.name ?: ""
            )

            override fun mapFrom(model: Equipment): EquipmentEntity {
                return EquipmentEntity(
                    id = model.id ?: -1,
                    image = model.image ?: "",
                    localizedName = model.localizedName,
                    name = model.name
                )
            }
        }

    }

}


abstract class ExtendedIngrMapperDB(
    private val measuresMapper: MeasuresMapperDB
): DoubleMapper<ExtendedIngredientEntity, ExtendedIngredient> {
    override fun mapTo(model: ExtendedIngredientEntity): ExtendedIngredient {
        return ExtendedIngredient(
            aisle = model.aisle ?: "",
            amount = model.amount ?: (-1).toDouble(),
            consistency = model.consistency ?: "",
            id = model.id,
            image = model.image ?: "",
            measures = model.measuresId?.let { id ->
                measuresMapper.mapTo(getMeasureById(id))
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

    override fun mapFrom(model: ExtendedIngredient): ExtendedIngredientEntity {
        return ExtendedIngredientEntity(
            aisle = model.aisle,
            amount = model.amount,
            consistency = model.consistency,
            id = model.id,
            image = model.image,
            measuresId = insertMeasuredId(model.measures),
            meta = model.meta,
            metaInformation = model.metaInformation,
            name = model.name,
            original = model.original,
            originalName = model.originalName,
            originalString = model.originalString,
            unit = model.unit
        )
    }

    abstract fun getMeasureById(id: Int) : MeasuresEntity

    abstract fun insertMeasuredId(measures: Measures): Int

    abstract class MeasuresMapperDB: DoubleMapper<MeasuresEntity, Measures> {

        val metricMapperDB = MetricMapperDB()
        val usMapperDB = UsMapperDB()

        override fun mapTo(model: MeasuresEntity): Measures {
            return Measures(
                metric = model.metricId?.let { id ->
                    MetricMapperDB().mapTo(getMetricById(id))
                } ?: Metric((-1).toDouble(), "", ""),
                us = model.usId?.let { id ->
                    UsMapperDB().mapTo(getUsBuId(id))
                } ?: Us((-1).toDouble(), "", "")
            )
        }

        override fun mapFrom(model: Measures): MeasuresEntity {
            return MeasuresEntity(
                metricId = insertMetric(model.metric),
                usId = insertUs(model.us),
                id = 0
            )
        }

        abstract fun getMetricById(id: Int): MetricEntity

        abstract fun getUsBuId(id: Int): UsEntity

        abstract fun insertMetric(metric: Metric): Int

        abstract fun insertUs(us: Us): Int

        inner class MetricMapperDB: DoubleMapper<MetricEntity, Metric> {
            override fun mapTo(model: MetricEntity): Metric = Metric(
                amount = model.amount ?: (-1).toDouble(),
                unitLong = model.unitLong ?: "",
                unitShort = model.unitShort ?: "")

            override fun mapFrom(model: Metric): MetricEntity {
                return MetricEntity(
                    amount = model.amount,
                    unitLong = model.unitLong,
                    unitShort = model.unitShort,
                    id = 0
                )
            }
        }
        inner class UsMapperDB: DoubleMapper<UsEntity, Us> {
            override fun mapTo(model: UsEntity): Us = Us(
                amount = model.amount ?: (-1).toDouble(),
                unitLong = model.unitLong ?: "",
                unitShort = model.unitShort ?: "")

            override fun mapFrom(model: Us): UsEntity {
                return UsEntity(
                    amount = model.amount,
                    unitLong = model.unitLong,
                    unitShort = model.unitShort,
                    id = 0
                )
            }
        }
    }
}