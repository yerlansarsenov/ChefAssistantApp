package kz.spoonacular.domain.model

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
sealed class Either<out T: Any?> {
    data class Success<out T: Any>(val response: T): Either<T>()
    data class Error(val error: String): Either<Nothing>()
}
