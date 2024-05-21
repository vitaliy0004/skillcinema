package com.example.final_android_lvl1.presentetion.basic.home.person

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.final_android_lvl1.data.retrofit.dto.detail.person.PersonDto

class PersonProfessionViewModule : ViewModel() {
    var professionOne = ""
    var professionTwo = ""
    var professionThree = ""
    var professionTour = ""
    var professionFive = ""
    val mapProfession = mutableMapOf<String, String>(
        "EDITOR" to "Монтажеры",
        "DESIGN" to "Художники",
        "COMPOSER" to "компазитор",
        "OPERATOR" to "Операторы",
        "WRITER" to "Сценарист",
        "VOCE_DIRECTOR" to "Режисёр дублежа",
        "PRODUCER" to "Продюссеры",
        "ACTOR" to "Актёр",
        "DIRECTOR" to "Режисёр",
        "HIMSELF" to "играет самого себя",
        "VOCE_MALE" to "муржской голос",
        "TRANSLATOR" to "переводчик",
        "HRONO_TITR_MALE" to "задний план",
        "HERSELF" to "играет самого себя",
        "HRONO_TITR_FEMALE" to "хроно-титры женьщина"
    )


    val chipOne = mutableListOf<Int>()
    val chipTwo = mutableListOf<Int>()
    val chipThree = mutableListOf<Int>()
    val chipFour = mutableListOf<Int>()
    val chipFive = mutableListOf<Int>()
    val mapChip = mutableMapOf<String, MutableList<Int>>()
    val listCollectionName = mutableListOf<String>()


    fun sort(person: PersonDto) {
        var professionRu = ""
        person.films.forEach {
            if (mapProfession[it.professionKey] == null)
                Log.d("main:", "${it.professionKey}")
            else professionRu = mapProfession[it.professionKey]!!
            when (professionRu) {
                professionOne -> chipOne.add(it.filmId)
                professionTwo -> chipTwo.add(it.filmId)
                professionThree -> chipThree.add(it.filmId)
                professionTour -> chipFour.add(it.filmId)
                professionFive -> chipFive.add(it.filmId)
                else -> {
                    when ("") {
                        professionOne -> {
                            professionOne = professionRu
                            chipOne.add(it.filmId)
                        }

                        professionTwo -> {
                            professionTwo = professionRu
                            chipTwo.add(it.filmId)
                        }

                        professionThree -> {
                            professionThree = professionRu
                            chipThree.add(it.filmId)
                        }

                        professionTour -> {
                            professionTour = professionRu
                            chipFour.add(it.filmId)
                        }

                        professionFive -> {
                            professionFive = professionRu
                            chipFive.add(it.filmId)
                        }
                    }
                }

            }
        }
        mapChip[professionOne] = chipOne
        if (professionTwo != "") mapChip[professionTwo] = chipTwo
        if (professionThree != "") mapChip[professionThree] = chipThree
        if (professionTour != "") mapChip[professionTour] = chipFour
        if (professionFive != "") mapChip[professionFive] = chipFive
        sortCollection()
    }

    fun sortCollection() {
        listCollectionName.add(professionOne)
        if (mapChip[professionTwo] != null && mapChip[professionTwo]!!.isEmpty())
            mapChip.remove(professionTwo)
        else if (mapChip[professionTwo] != null) listCollectionName.add(professionTwo)


        if (mapChip[professionThree] != null && mapChip[professionThree]!!.isEmpty())
            mapChip.remove(professionThree)
        else if (mapChip[professionThree] != null) listCollectionName.add(professionThree)

        if (mapChip[professionTour] != null && mapChip[professionTour]!!.isEmpty()) mapChip.remove(
            professionTour
        )
        else if (mapChip[professionTour] != null) listCollectionName.add(professionTour)

        if (mapChip[professionFive] != null && mapChip[professionFive]!!.isEmpty()) mapChip.remove(
            professionFive
        )
        else if (mapChip[professionFive] != null) listCollectionName.add(professionFive)
    }
}