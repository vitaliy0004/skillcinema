package com.example.final_android_lvl1.data.retrofit.dto.detail.person

import com.example.final_android_lvl1.entity.retrofit.detal.person.Person
import com.google.gson.annotations.SerializedName

data class PersonDto(
    @SerializedName("age") override val age: Int,
    @SerializedName("birthday") override val birthday: Any,
    @SerializedName("birthplace") override val birthplace: Any,
    @SerializedName("death") override val death: Any,
    @SerializedName("deathplace") override val deathplace: Any,
    @SerializedName("facts") override val facts: List<Any>,
    @SerializedName("films") override val films: List<FilmsDto>,
    @SerializedName("growth") override val growth: Int,
    @SerializedName("hasAwards") override val hasAwards: Int,
    @SerializedName("nameEn") override val nameEn: String,
    @SerializedName("nameRu") override val nameRu: String,
    @SerializedName("personId") override val personId: Int,
    @SerializedName("posterUrl") override val posterUrl: String,
    @SerializedName("profession") override val profession: String,
    @SerializedName("sex") override val sex: String,
    @SerializedName("spouses") override val spouses: List<Any>,
    @SerializedName("webUrl") override val webUrl: String
) : Person