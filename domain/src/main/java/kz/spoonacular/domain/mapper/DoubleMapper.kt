package kz.spoonacular.domain.mapper

/**
 * Created by Sarsenov Yerlan on 18.02.2021.
 */
interface DoubleMapper<T, R> {
    fun mapTo(model: T): R
    fun mapFrom(model: R): T
}
