package com.example.final_android_lvl1.data.retrofit

import com.example.final_android_lvl1.data.retrofit.dto.all_filter.FilterDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.AllFilmParticipantsDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.DetailDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.GalleryDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.person.PersonDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.similar_movies.SimilarMoviesDto
import com.example.final_android_lvl1.data.retrofit.dto.filter_country_genre.CountryAndGenreDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.FilmDto
import com.example.final_android_lvl1.data.retrofit.dto.serialDto.SerialDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FinishURL {
    @GET("api/v2.2/films/premieres")
    suspend fun premiers(
        @Query("year") year: Int,
        @Query("month") month: String,
        @Query("page") page: Int
    ): FilmDto

    @GET("api/v2.2/films/top")
    suspend fun topFilm(@Query("page") page: Int): FilmDto

    @GET("api/v2.2/films")
    suspend fun miniSeries(@Query("type") MiniSeries: String, @Query("page") page: Int): FilmDto

    @GET("api/v2.2/films")
    suspend fun popular(
        @Query("order") order: String = "NUM_VOTE",
        @Query("page") page: Int
    ): FilmDto

    @GET("api/v2.2/films")
    suspend fun countryAndGenre(
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("page") page: Int
    ): FilmDto

    //filter
    @GET("api/v2.2/films/filters")
    suspend fun filterCountryAndGenre(): CountryAndGenreDto

    //detail
    @GET("api/v2.2/films/{id}")
    suspend fun detail(@Path("id") id: Int): DetailDto

    //serials
    @GET("api/v2.2/films/{id}/seasons")
    suspend fun episode(@Path("id") id: Int): SerialDto

    //allFilmParticipants
    @GET("api/v1/staff")
    suspend fun allFilmParticipants(@Query("filmId") filmId: Int): AllFilmParticipantsDto

    @GET("api/v2.2/films/{id}/images")
    suspend fun gallery(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("type") type: String
    ): GalleryDto

    @GET("api/v2.2/films/{id}/similars")
    suspend fun similarMovies(@Path("id") id: Int): SimilarMoviesDto

    @GET("api/v1/staff/{id}")
    suspend fun person(@Path("id") id: Int): PersonDto

    @GET("api/v2.2/films")
    suspend fun filter(
        @QueryMap filterString: Map<String, String>,
        @QueryMap filterInt: Map<String, Int>,
        @Query("page") page: Int
    ): FilterDto
}