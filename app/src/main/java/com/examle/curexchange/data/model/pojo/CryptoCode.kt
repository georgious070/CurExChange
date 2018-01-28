package com.examle.curexchange.data.model.pojo

import com.google.gson.annotations.SerializedName

data class CryptoCode (@SerializedName("rows") val rows: MutableList<Row>)