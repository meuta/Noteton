package com.obrigada_eu.noteton.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WordEntryDto(

    @SerializedName("sense")
    @Expose
    val sense: Int?,
    @SerializedName("word")
    @Expose
    val word: String,
    @SerializedName("xml")
    @Expose
    val xml: String?,
)