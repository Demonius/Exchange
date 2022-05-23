package by.dmitry.data.models.response

import by.dmitry.data.models.BaseResponse
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ResponseCurrencies(
    @SerializedName("symbols")
    val symbols: JsonObject,

    @SerializedName("success")
    override val success: Boolean
) : BaseResponse
