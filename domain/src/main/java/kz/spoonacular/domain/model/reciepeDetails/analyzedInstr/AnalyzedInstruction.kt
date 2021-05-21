package kz.spoonacular.domain.model.reciepeDetails.analyzedInstr

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)
