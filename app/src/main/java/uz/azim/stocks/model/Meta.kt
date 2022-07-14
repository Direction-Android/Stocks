package uz.azim.stocks.model

import com.google.gson.annotations.SerializedName

data class Meta(
    val copyright: String,
    @SerializedName("data_status")
    val dataStatus:String
)
