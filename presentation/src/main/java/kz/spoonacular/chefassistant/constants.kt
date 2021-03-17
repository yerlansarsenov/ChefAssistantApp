package kz.spoonacular.chefassistant


/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */

sealed class Types(val value: String) {
    class MainCourse : Types("main course")
    class SideDish : Types("side dish")
    class Dessert : Types("dessert")
    class Appetizer :Types("appetizer")
    class SALAD :Types("salad")
    class Bread: Types("bread")
    class Breakfast: Types("breakfast")
    class Soup: Types("soup")
    class Beverage: Types("beverage")
    class Sauce: Types("sauce")
    class Marinade: Types("marinade")
    class FingerFood: Types("fingerfood")
    class Snack: Types("snack")
    class Drink : Types("drink")
    class None : Types("")
}

sealed class Cuisines(val value: String) {
    class African: Cuisines("African")
    class American: Cuisines("American")
    class British: Cuisines("British")
    class Cajun: Cuisines("Cajun")
    class Caribbean: Cuisines("Caribbean")
    class Chinese: Cuisines("Chinese")
    class EasternEuropean: Cuisines("EasternEuropean")
    class European: Cuisines("European")
    class French: Cuisines("French")
    class German: Cuisines("German")
    class Greek: Cuisines("Greek")
    class Indian: Cuisines("Indian")
    class Irish: Cuisines("Irish")
    class Italian: Cuisines("Italian")
    class Japanese: Cuisines("Japanese")
    class Jewish: Cuisines("Jewish")
    class Korean: Cuisines("Korean")
    class LatinAmerican: Cuisines("LatinAmerican")
    class Mediterranean: Cuisines("Mediterranean")
    class Mexican: Cuisines("Mexican")
    class MiddleEastern: Cuisines("MiddleEastern")
    class Nordic: Cuisines("Nordic")
    class Southern: Cuisines("Southern")
    class Spanish: Cuisines("Spanish")
    class Thai: Cuisines("Thai")
    class Vietnamese: Cuisines("Vietnamese")
}