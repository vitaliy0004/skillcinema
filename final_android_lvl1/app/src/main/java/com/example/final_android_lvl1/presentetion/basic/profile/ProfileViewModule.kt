package com.example.final_android_lvl1.presentetion.basic.profile

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.databinding.MainItemFilmWasFilmedBinding
import com.example.final_android_lvl1.domain.RepositoryDatabase
import com.example.final_android_lvl1.entity.database.CollectionFilmDatabase
import com.example.final_android_lvl1.entity.retrofit.preview.ListFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModule @Inject constructor(private val repositoryDatabase: RepositoryDatabase) :
    ViewModel() {
    val allCollection = this.repositoryDatabase.allCollectionFilm()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000L),
            initialValue = emptyList()
        )
    val listFilm = mutableListOf<ListFilm>()
    val _listViewedFilm = MutableStateFlow<MutableSet<ListFilmDto>>(mutableSetOf<ListFilmDto>())
    val listViewedFilm = _listViewedFilm.asStateFlow()
    fun isStandardCollections() {
        viewModelScope.launch {
            val nameCollection =
                listOf("Просмотренно", "Вам было интерестно", "любимое", "хочу посмотреть")
            for (counter in 1..4) {
                repositoryDatabase.addCollection(
                    CollectionFilmDatabase(
                        idCollection = counter,
                        nameCollection = nameCollection[counter - 1]
                    )
                )
            }
        }
    }

    suspend fun saveCollection(idCollection: Int): List<ListFilmDto> {
        return repositoryDatabase.getCollection(idCollection)
    }

    fun viewCounter(binding: MainItemFilmWasFilmedBinding, listFilm: List<ListFilmDto>) {
        if (listFilm.size > 10)
            binding.counter.text = listFilm.size.toString()
        else binding.layoutTableOfContents.visibility = View.INVISIBLE
    }


    fun addCollection(nameCollection: String) {
        viewModelScope.launch {
            repositoryDatabase.addCollection(
                CollectionFilmDatabase(
                    nameCollection = nameCollection
                )
            )
        }
    }

    suspend fun removeCollectionFilm(idCollection: Int) {
        repositoryDatabase.collectionFilmDelete(idCollection = idCollection)
    }

    fun addFim(film: ListFilmDto, idCollection: Int) {
        viewModelScope.launch {
            repositoryDatabase.addFilm(film, idCollection)
        }
    }

    fun removeCollection(idCollection: Int) {
        viewModelScope.launch {

            repositoryDatabase.collectionDelete(idCollection = idCollection)
            repositoryDatabase.collectionFilmDelete(idCollection = idCollection)
        }
    }

    fun removeFilm(film: ListFilmDto, idCollection: Int) {
        viewModelScope.launch {
            val idFilm = if (film.kinopoiskId == null || film.kinopoiskId == 0) film.filmId
            else film.kinopoiskId
            val idFilmAndCollection = idCollection * 100_000_000 + idFilm
            repositoryDatabase.filmDelete(idFilmAndCollection)
        }
    }
}