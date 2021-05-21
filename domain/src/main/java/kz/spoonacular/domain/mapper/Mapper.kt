package kz.spoonacular.domain.mapper

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
interface Mapper<T, R> {
    fun map(model: T): R
}
