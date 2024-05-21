package com.example.final_android_lvl1.entity.retrofit.detal.person

import com.example.final_android_lvl1.data.retrofit.dto.detail.person.FilmsDto

interface Person {
    val age: Int
    val birthday: Any
    val birthplace: Any
    val death: Any
    val deathplace: Any
    val facts: List<Any>
    val films: List<FilmsDto>
    val growth: Int
    val hasAwards: Int
    val nameEn: String
    val nameRu: String
    val personId: Int
    val posterUrl: String
    val profession: String
    val sex: String
    val spouses: List<Any>
    val webUrl: String
}