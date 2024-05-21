package com.example.final_android_lvl1.presentetion.basic.home.season

import androidx.lifecycle.ViewModel
import com.example.final_android_lvl1.data.retrofit.dto.detail.DetailDto
import com.example.final_android_lvl1.data.retrofit.dto.serialDto.EpisodeDto
import com.example.final_android_lvl1.data.retrofit.dto.serialDto.SerialDto

class SeasonViewModule : ViewModel() {
    val mapEpisode = mutableMapOf<Int, List<EpisodeDto>>()
    val listSeason = mutableListOf<Int>()

    fun sortSeason(seasonFilm: SerialDto) {
        seasonFilm.items.forEach {
            listSeason.add(it.number)
            mapEpisode[it.number] = it.episodes
        }
    }

    fun nameCollection(detailFilm: DetailDto): String {
        return if (detailFilm.nameRu == null) filterNameCollection(detailFilm.nameOriginal)
        else filterNameCollection(detailFilm.nameRu)
    }

    fun filterNameCollection(collection: String): String {
        return if (collection.length > 26) collection.filterIndexed { index, c -> index < 23 } + "..."
        else collection
    }


    fun month(monthString: String): String {
        return when (monthString.toInt()) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "авгуса"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            else -> "декобря"
        }
    }

    fun data(data: String): String {
        var counter = 0
        var stringData = ""
        var dataString = ""
        data.forEach {
            counter += 1
            when (counter) {
                in 1..3 -> stringData += it
                4 -> {
                    stringData += it
                    dataString = stringData
                    stringData = ""
                }

                6 -> stringData += it
                7 -> {
                    stringData += it
                    dataString = "${month(stringData)} $dataString"
                    stringData = ""
                }

                9 -> stringData += it
                10 -> {
                    stringData += it
                    dataString = "$stringData $dataString"
                    stringData = ""
                }

            }
        }
        return dataString
    }

}