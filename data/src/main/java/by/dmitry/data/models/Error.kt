package by.dmitry.data.models

data class Error(
    override val success: Boolean,
    val message: String
) : BaseResponse
