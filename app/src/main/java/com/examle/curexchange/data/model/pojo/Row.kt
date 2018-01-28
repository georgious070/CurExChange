package com.examle.curexchange.data.model.pojo

import com.google.gson.annotations.SerializedName

data class Row (@SerializedName("code") val code: String,
                @SerializedName("name") val name: String,
                @SerializedName("statuses") val statuses: List<String>)