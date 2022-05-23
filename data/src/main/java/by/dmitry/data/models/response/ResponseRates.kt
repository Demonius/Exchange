package by.dmitry.data.models.response

import by.dmitry.data.models.BaseResponse
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ResponseRates(
    @SerializedName("success")
    override val success: Boolean,

    @SerializedName("base")
    val base: String,

    @SerializedName("rates")
    val rates: JsonObject
) : BaseResponse
