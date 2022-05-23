package by.dmitry.data.models.mapper

internal interface IMapper<E, D> {

    fun mapToEntity(type: D): E

    fun mapFromEntity(type: E): D

    fun stringToFloat(string: String?) = if (string.isNullOrEmpty()) {
        0f
    } else {
        string.replace(",", ".").toFloat()
    }
}