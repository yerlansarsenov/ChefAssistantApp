package kz.spoonacular.data.mapper.db_mapper

import kz.spoonacular.data.model.db_models_new.detailed_entities.RecipeDetailsEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr.AnalyzedInstructionEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr.EquipmentEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr.IngredientEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr.StepEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr.ExtendedIngredientEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr.MeasuresEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr.MetricEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr.UsEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.winePairing.ProductMatcheEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.winePairing.WinePairingEntity
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
            analyzedInstructions = model.analyzedInstructions?.map { item ->
                analyzedMapper.mapTo(item)
            } ?: emptyList(),
            cheap = model.cheap ?: false,
            cookingMinutes = model.cookingMinutes ?: -1,
            creditsText = model.creditsText ?: "",
            cuisines = model.cuisines ?: emptyList(),
            dairyFree = model.dairyFree ?: false,
            diets = model.diets ?: emptyList(),
            dishTypes = model.dishTypes ?: emptyList(),
            extendedIngredients = model.extendedIngredients?.let { list ->
                list.map { item ->
                    extendedIngrMapper.mapTo(item)
                }
            } ?: emptyList(),
            glutenFree = model.glutenFree ?: false,
            healthScore = model.healthScore ?: -1,
            id = model.id,
            image = model.image ?: "",
            instructions = model.instructions ?: "",
            license = model.license ?: "",
            lowFodmap = model.lowFodmap ?: false,
            originalId = model.original,
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
            winePairing = model.winePairing?.let { wineMapper.mapTo(it) } ?: WinePairing(
                emptyList(), "", emptyList())
        )
    }

    override fun mapFrom(model: RecipeDetailed): RecipeDetailsEntity {
        return RecipeDetailsEntity(
            analyzedInstructions =
                model.analyzedInstructions.map {
                    analyzedMapper.mapFrom(it)
                },
            cheap = model.cheap,
            cookingMinutes = model.cookingMinutes,
            creditsText = model.creditsText,
            cuisines = model.cuisines,
            dairyFree = model.dairyFree,
            diets = model.diets,
            dishTypes = model.dishTypes,
            extendedIngredients =
                model.extendedIngredients.map {
                    extendedIngrMapper.mapFrom(it)
                },
            glutenFree = model.glutenFree,
            healthScore = model.healthScore,
            id = model.id,
            image = model.image,
            instructions = model.instructions,
            license = model.license,
            lowFodmap = model.lowFodmap ?: false,
            original = model.originalId,
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
            winePairing = wineMapper.mapFrom(model.winePairing)
        )
    }
}

abstract class WineMapperDB: DoubleMapper<WinePairingEntity, WinePairing> {
    override fun mapTo(model: WinePairingEntity): WinePairing {
        val productMatcheMapper = ProductMatchesMapperDB()
        return WinePairing(
            pairedWines = model.pairedWines ?: emptyList(),
            pairingText = model.pairingText ?: "",
            productMatches =
                model.productMatches?.map { id ->
                    productMatcheMapper.mapTo(id)
                } ?: emptyList()
        )
    }

    override fun mapFrom(model: WinePairing): WinePairingEntity {
        val productMatcheMapper = ProductMatchesMapperDB()
        return WinePairingEntity(
            pairedWines = model.pairedWines ?: emptyList(),
            pairingText = model.pairingText ?: "",
            productMatches =
                model.productMatches.map {
                    productMatcheMapper.mapFrom(it)
                },
            id = 0
        )
    }

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
            steps =
                model.steps?.map { item ->
                    stepsMapper.mapTo(item)
                } ?: emptyList()
        )
    }

    override fun mapFrom(model: AnalyzedInstruction): AnalyzedInstructionEntity {
        return AnalyzedInstructionEntity(
            name = model.name,
            steps =
                model.steps.map {
                    stepsMapper.mapFrom(it)
                },
            id = 0
        )
    }

    abstract class StepsMapperDB: DoubleMapper<StepEntity, Step> {
        val ingredientsMapper = IngredientsMapperDB()

        val equipmentsMapper = EquipmentsMapperDB()

        override fun mapTo(model: StepEntity): Step = Step(
                ingredients = model.ingredients?.map { item ->
                    ingredientsMapper.mapTo(item)
                } ?: emptyList(),
                equipments = model.equipments?.map { item ->
                    equipmentsMapper.mapTo(item)
                } ?: emptyList(),
                number = model.number ?: -1,
                step = model.step ?: ""
            )

        override fun mapFrom(model: Step): StepEntity {
            return StepEntity(
                ingredients = model.ingredients.map {
                    ingredientsMapper.mapFrom(it)
                },
                equipments = model.equipments.map {
                    equipmentsMapper.mapFrom(it)
                },
                number = model.number,
                step = model.step,
                id = 0
            )
        }

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
            measures =
                model.measures?.let { item ->
                    measuresMapper.mapTo(item)
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
            measures =
                model.measures.let {
                    measuresMapper.mapFrom(it)
                },
            meta = model.meta,
            metaInformation = model.metaInformation,
            name = model.name,
            original = model.original,
            originalName = model.originalName,
            originalString = model.originalString,
            unit = model.unit
        )
    }

    abstract class MeasuresMapperDB: DoubleMapper<MeasuresEntity, Measures> {

        override fun mapTo(model: MeasuresEntity): Measures {
            return Measures(
                metric =
                    model.metric?.let { item ->
                        MetricMapperDB().mapTo(item)
                    } ?: Metric((-1).toDouble(), "", ""),
                us =
                    model.us?.let { item ->
                        UsMapperDB().mapTo(item)
                    } ?: Us((-1).toDouble(), "", "")
            )
        }

        override fun mapFrom(model: Measures): MeasuresEntity {
            return MeasuresEntity(
                metric =
                    model.metric.let { item ->
                        MetricMapperDB().mapFrom(item)
                    },
                us =
                    model.us.let { item ->
                        UsMapperDB().mapFrom(item)
                    },
                id = 0
            )
        }

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