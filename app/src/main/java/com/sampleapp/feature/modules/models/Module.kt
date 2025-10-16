package com.sampleapp.feature.modules.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Module(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("questions_url")
    val questionsUrl: String? = null

) : Parcelable