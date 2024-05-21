package com.example.final_android_lvl1.presentetion.basic.home.person

import androidx.lifecycle.ViewModel
import com.example.final_android_lvl1.data.retrofit.dto.detail.DetailDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.person.FilmsDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.person.PersonDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.domain.Repository
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class PersonViewModule @Inject constructor(private val repository: Repository) : ViewModel() {
    lateinit var person: PersonDto
    var isChecked = true
    suspend fun person(id: Int) {
        person = repository.person(id)
    }

    suspend fun detail(id: Int): DetailDto {
        return repository.detail(id)
    }

    suspend fun bestFilm(listFilmDto: List<FilmsDto>, viewModel: MainViewModel, controller: Int) {

        val listDetail = mutableSetOf<ListFilmDto>()
        listFilmDto.forEach {
            if (controller == 1) {
                if (listDetail.size < 10) {
                    val detailFilm = if (viewModel.listDetailFilm[it.filmId] == null) {
                        viewModel.listDetailFilm[it.filmId] = detail(it.filmId)
                        viewModel.listDetailFilm[it.filmId]!!
                    } else viewModel.listDetailFilm[it.filmId]!!
                    listDetail.add(viewModel.formatListFilm(detailFilm))
                }
            } else {
                val detailFilm = if (viewModel.listDetailFilm[it.filmId] == null) {
                    // чтобы сайт не думал что это спам
                    delay(100)
                    viewModel.listDetailFilm[it.filmId] = detail(it.filmId)
                    viewModel.listDetailFilm[it.filmId]!!
                } else viewModel.listDetailFilm[it.filmId]!!
                listDetail.add(
                    viewModel.formatListFilm(detailFilm)
                )
            }
        }
        viewModel.listFilm[viewModel.counterBottomNavigation]!!.add(listDetail.toList())

    }

}