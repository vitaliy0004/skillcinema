package com.example.final_android_lvl1.presentetion.basic.home.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.final_android_lvl1.R
import com.example.final_android_lvl1.data.retrofit.dto.detail.DetailDto
import com.example.final_android_lvl1.data.retrofit.dto.detail.gallery.ItemGalleryDto
import com.example.final_android_lvl1.data.retrofit.dto.preview.ListFilmDto
import com.example.final_android_lvl1.data.retrofit.dto.serialDto.SerialDto
import com.example.final_android_lvl1.databinding.MainDialogCollectionBinding
import com.example.final_android_lvl1.databinding.MainDialogErrorBinding
import com.example.final_android_lvl1.databinding.MainDialogFilmBinding
import com.example.final_android_lvl1.databinding.MainDialogUrlBinding
import com.example.final_android_lvl1.databinding.MainFragmentDetailFilmBinding
import com.example.final_android_lvl1.entity.database.AllInfoDatabase
import com.example.final_android_lvl1.presentetion.basic.MainViewModel
import com.example.final_android_lvl1.presentetion.basic.home.AdapterFilmPremieres
import com.example.final_android_lvl1.presentetion.basic.profile.ProfileViewModule
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@AndroidEntryPoint
class DetailFilmFragment : Fragment() {
    @Inject
    lateinit var factory: FactoryDetail
    private val detailViewModel: DetailViewModel by viewModels { factory }
    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: MainFragmentDetailFilmBinding? = null
    private lateinit var detailName: DetailDto
    private lateinit var adapterActor: AdapterAllFilmParticipants
    private val binding get() = _binding!!
    private var season: SerialDto? = null
    private lateinit var gallery: List<ItemGalleryDto>
    private lateinit var similarMovies: List<ListFilmDto>
    private var idFilmDetail: Int = 0
    private lateinit var dialog: BottomSheetDialog
    private val profileViewModule: ProfileViewModule by viewModels()
    private lateinit var jobRequest: Job
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentDetailFilmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.isBack)
            viewModel.bottomNavigation[viewModel.counterBottomNavigation]!!.add(R.id.detailFilmFragment)
        viewModel.isBack = false
        viewModel.backPressed(requireActivity(), viewLifecycleOwner, R.id.detailFilmFragment)
        dialog = BottomSheetDialog(requireContext())
        idFilmDetail = viewModel.idFilm[viewModel.counterBottomNavigation]!!.last()
        detailViewModel.listActor.clear()
        detailViewModel.listNOActor.clear()


        with(binding.detailInform) {
            share.setOnClickListener { shareDialog() }
            more.setOnClickListener {
                try {
                    collectionDialog()
                } catch (e: Exception) {
                    errorDialog()
                }

            }


            binding.detailInform.view.setOnClickListener {
                profileViewModule.removeFilm(viewModel.formatListFilm(detailName), 1)
                binding.detailInform.view.visibility = View.GONE
                viewOf.visibility = View.VISIBLE
            }
            viewOf.setOnClickListener {
                profileViewModule.addFim(viewModel.formatListFilm(detailName), 1)
                binding.detailInform.view.visibility = View.VISIBLE
                viewOf.visibility = View.GONE
            }
            favorite.setOnClickListener {
                favorite.visibility = View.GONE
                noFavorite.visibility = View.VISIBLE
                profileViewModule.removeFilm(viewModel.formatListFilm(detailName), 3)
            }
            noFavorite.setOnClickListener {
                favorite.visibility = View.VISIBLE
                noFavorite.visibility = View.GONE
                profileViewModule.addFim(viewModel.formatListFilm(detailName), 3)
            }
            bookmark.setOnClickListener {
                bookmark.visibility = View.GONE
                bookmarkOf.visibility = View.VISIBLE
                profileViewModule.removeFilm(viewModel.formatListFilm(detailName), 4)
            }
            bookmarkOf.setOnClickListener {
                bookmark.visibility = View.VISIBLE
                bookmarkOf.visibility = View.GONE
                profileViewModule.addFim(viewModel.formatListFilm(detailName), 4)
            }
            episode.all.setOnClickListener {
                findNavController().navigate(R.id.action_detailFilmFragment_to_seasonFilmFragment)
            }
            binding.back.setOnClickListener {
                viewModel.backArrow(R.id.detailFilmFragment, requireActivity())
            }
            detailGallery.layoutTableOfContents.setOnClickListener {
                findNavController().navigate(R.id.action_detailFilmFragment_to_galleryCollectionPhoto)
            }
            detailSimilarMovies.layoutTableOfContents.setOnClickListener {
                allCollection()
            }
            detailInformActor.layoutTableOfContents.setOnClickListener {

                viewModel.nameCollection[viewModel.counterBottomNavigation]!!.add("Актёры")
                viewModel.listWorkerAndActor[viewModel.counterBottomNavigation]!!.add(
                    detailViewModel.listActor
                )
                findNavController().navigate(R.id.action_detailFilmFragment_to_allPersonFragment)
            }
            detailInformWorkers.layoutTableOfContents.setOnClickListener {
                viewModel.nameCollection[viewModel.counterBottomNavigation]!!.add("Над фильмом работали")
                viewModel.listWorkerAndActor[viewModel.counterBottomNavigation]!!.add(
                    detailViewModel.listNOActor
                )
                findNavController().navigate(R.id.action_detailFilmFragment_to_allPersonFragment)
            }
            descriptionPreview.setOnClickListener {
                if (detailName.description.length > 250) {
                    if (descriptionPreview.text.length < 252) descriptionPreview.text =
                        detailName.description
                    else descriptionPreview.text =
                        detailViewModel.description(detailName.description)
                }
            }

            lifecycleScope.launch { request() }

            detailInformActor.text.text = getString(R.string.the_film_was_filmed)
            detailInformWorkers.text.text = getString(R.string.we_were_working_on_the_film)
            detailGallery.text.text = getString(R.string.gallery)
            detailSimilarMovies.text.text = getString(R.string.similar_films)

        }
    }

    private fun errorDialog() {
        val bingingDialog = MainDialogErrorBinding.inflate(layoutInflater)
        bingingDialog.close.setOnClickListener {
            dialog.dismiss()
        }
        dialogShow(bingingDialog.root)
    }

    private fun dialogShow(bingingDialog: ConstraintLayout) {
        dialog.setCancelable(false)
        dialog.setContentView(bingingDialog)
        dialog.show()
    }

    private fun shareDialog() {
        val bindingDialog = MainDialogUrlBinding.inflate(layoutInflater)
        bindingDialog.close.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.URL.text = resources.getString(
            R.string.URL_film,
            viewModel.idFilm[viewModel.counterBottomNavigation]!!.last()
        )
        dialogShow(bindingDialog.root)
    }

    private lateinit var adapterCheckbox: AdapterCollectionCheckbox
    private fun collectionDialog() {
        val bindingDialog = MainDialogFilmBinding.inflate(layoutInflater)
        viewModel.workerFilms(
            bindingDialog.detailFilm,
            viewModel.formatListFilm(detailName),
            { _ -> Unit }
        )

        val job = lifecycleScope.launch {
            profileViewModule.allCollection.collect { listAllInfo ->
                val listFilmDatabase = mutableListOf<AllInfoDatabase>()
                listAllInfo.forEachIndexed { index, allInfoDatabase ->
                    if (index == 0){
                        if (
                            binding.detailInform.view.visibility == View.VISIBLE
                            || binding.detailInform.viewOf.visibility == View.VISIBLE
                        ) listFilmDatabase.add(allInfoDatabase)
                    } else listFilmDatabase.add(allInfoDatabase)
                }
                adapterCheckbox = AdapterCollectionCheckbox(
                    listFilmDatabase,
                    viewModel.formatListFilm(detailName),
                    { film, idCollection -> profileViewModule.addFim(film, idCollection) },
                    { film, idCollection -> profileViewModule.removeFilm(film, idCollection) }
                )

                bindingDialog.recyclerView.adapter = adapterCheckbox
                bindingDialog.layoutAddCollection.setOnClickListener { addCollection() }
                dialogShow(bindingDialog.root)
            }
        }
        lifecycleScope.launch {
            bindingDialog.close.setOnClickListener {
                lifecycleScope.launch {
                    isCheckedCollection()
                    job.cancel()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun addCollection() {
        val dialog = BottomSheetDialog(requireContext())
        val bindingDialog = MainDialogCollectionBinding.inflate(layoutInflater)
        bindingDialog.close.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.finish.setOnClickListener {
            profileViewModule.addCollection(bindingDialog.nameCollection.text.toString())
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(bindingDialog.root)
        dialog.show()
    }

    private fun request() {
        jobRequest = lifecycleScope.launch {
            if (viewModel.isOnline(requireContext())) {
                binding.layoutDetailInform.visibility = View.INVISIBLE
                binding.layoutWifi.visibility = View.INVISIBLE
                binding.progressbar.visibility = View.VISIBLE

                //проверяем был ли запрос по этому фильму
                if (viewModel.listDetailFilm[idFilmDetail] == null || viewModel.listSimilarMovies[idFilmDetail] == null) {
                    detailName = detailViewModel.detail(idFilmDetail)
                    viewModel.listDetailFilm[idFilmDetail] = detailName
                    val allFilmParticipants = try {
                        detailViewModel.allFilmParticipants(
                            viewModel.idFilm[viewModel.counterBottomNavigation]!!.last()
                        )
                    } catch (e: Exception) {
                        null
                    }
                    detailViewModel.sortActorAndWorker(allFilmParticipants)
                    viewModel.listAllFilmParticipants[idFilmDetail] = allFilmParticipants

                    season = detailViewModel.season(
                        detailName.serial,
                        idFilmDetail,
                        binding.detailInform
                    )
                    viewModel.listEpisode[idFilmDetail] = season

                    gallery = detailViewModel.gallery(idFilmDetail, "STILL")
                    viewModel.listGallery[idFilmDetail] = gallery

                    similarMovies = detailViewModel.similarMovies(idFilmDetail, viewModel)
                    viewModel.listSimilarMovies[idFilmDetail] = similarMovies
                } else {
                    noWifi()
                }
                infoDetail()
                binding.progressbar.visibility = View.GONE
                binding.layoutDetailInform.visibility = View.VISIBLE
            } else {
                if (viewModel.listDetailFilm[idFilmDetail] == null || viewModel.listGallery[idFilmDetail] == null) {
                    binding.layoutDetailInform.visibility = View.INVISIBLE
                    binding.layoutWifi.visibility = View.VISIBLE
                    binding.progressbar.visibility = View.GONE
                } else {
                    noWifi()
                    infoDetail()
                }
            }
            binding.wifi.textView.setOnClickListener {
                lifecycleScope.launch { request() }
            }
        }
    }

    private fun noWifi() {
        detailName = viewModel.listDetailFilm[idFilmDetail]!!
        if (viewModel.listAllFilmParticipants[idFilmDetail] != null)
            detailViewModel.sortActorAndWorker(viewModel.listAllFilmParticipants[idFilmDetail]!!)
        season =
            detailViewModel.repeatSeason(viewModel.listEpisode[idFilmDetail], binding.detailInform)
        if (viewModel.listGallery[idFilmDetail] != null)
            gallery = viewModel.listGallery[idFilmDetail]!!
        if (viewModel.listSimilarMovies[idFilmDetail] != null)
            similarMovies = viewModel.listSimilarMovies[idFilmDetail]!!
    }

    private suspend fun isCheckedCollection() {
        if (detailViewModel.premieresFilm(
                viewModel.premieresFilm,
                viewModel.idFilm[viewModel.counterBottomNavigation]!!.last()
            )
        ) {
            binding.detailInform.view.visibility = View.GONE
            binding.detailInform.viewOf.visibility = View.GONE
        } else detailViewModel.viewFilm(
            binding.detailInform,
            profileViewModule,
            viewModel.formatListFilm(detailName)
        )
        viewModel.isFavorite(
            binding.detailInform,
            profileViewModule,
            viewModel.formatListFilm(detailName)
        )
        viewModel.isBookmark(
            binding.detailInform,
            profileViewModule,
            viewModel.formatListFilm(detailName)
        )

    }

    private suspend fun infoDetail() {
        if (viewModel.listDetailFilm[idFilmDetail] != null && viewModel.listGallery[idFilmDetail] != null) {
            with(binding.detailInform) {
                poster.alpha = 0.8f
                ratingName.text = getString(
                    R.string.rating_and_name,
                    detailViewModel.rating(detailName.ratingKinopoisk),
                    detailViewModel.filmName(
                        detailName.nameOriginal,
                        detailName.nameRu
                    )
                )
                yearsGenre.text = getString(
                    R.string.years_and_genre,
                    detailName.year.toString(),
                    detailViewModel.filmOrSerial(detailName, season)
                )
                countryTimeOld.text = getString(
                    R.string.country_and_time_old,
                    detailViewModel.country(detailName.countries),
                    detailViewModel.timeFilm(detailName.filmLength),
                    detailViewModel.ageLimits(detailName.ratingAgeLimits)
                )
                nameFilm.text = detailName.nameRu
                Glide
                    .with(poster.context)
                    .load(detailName.posterUrl)
                    .into(poster)
                slide.text = detailName.slogan
                descriptionPreview.text = detailViewModel.description(detailName.description)
                episode.episode.text = detailViewModel.episodeText(season)


                isCheckedCollection()

                // если актеров нету то смыла отражать список актёров нету
                if (detailViewModel.listActor.size < 1) {
                    listActors.visibility = View.GONE
                    detailInformActor.mainItemFilmWasFilmed.visibility = View.GONE
                } else {
                    adapterActor =
                        AdapterAllFilmParticipants(detailViewModel.listActor) { id -> person(id) }
                    listActors.adapter = adapterActor
                    detailInformActor.counter.text = detailViewModel.listActor.size.toString()
                    detailViewModel.viewPeople(detailInformActor, detailViewModel.listActor.size)
                }

                if (detailViewModel.listNOActor.isEmpty()) {
                    listWorkers.visibility = View.GONE
                    detailInformWorkers.mainItemFilmWasFilmed.visibility = View.GONE
                } else {
                    adapterActor =
                        AdapterAllFilmParticipants(detailViewModel.listNOActor) { id -> person(id) }
                    listWorkers.adapter = adapterActor
                    detailInformWorkers.counter.text = detailViewModel.listNOActor.size.toString()
                    detailViewModel.viewPeople(
                        detailInformWorkers,
                        detailViewModel.listNOActor.size
                    )

                }
                if (gallery.isEmpty()) {
                    listGallery.visibility = View.GONE
                    detailGallery.mainItemFilmWasFilmed.visibility = View.GONE
                } else {
                    val adapterGallery = AdapterGallery({ position, list ->
                        viewModel.photoDetail(
                            position,
                            findNavController().navigate(R.id.action_detailFilmFragment_to_galleryPhoto),
                            list
                        )
                    }, gallery)
                    listGallery.adapter = adapterGallery
                    detailGallery.counter.text = gallery.size.toString()
                    viewModel.listPhotoGallery[viewModel.counterBottomNavigation] = gallery
                    detailViewModel.viewPeople(detailGallery, gallery.size)
                }

                if (similarMovies.isEmpty()) {
                    listSimilarMovies.visibility = View.GONE
                    detailSimilarMovies.mainItemFilmWasFilmed.visibility = View.GONE
                } else {
                    val adapterSimilarMovies = AdapterFilmPremieres(
                        profileViewModule,
                        similarMovies,
                        viewModel,
                        { allCollection() },
                        { id -> detailFilm(id) },
                        { listFilmDto -> profileViewModule.addFim(listFilmDto, 1) },
                        { listFilmDto -> profileViewModule.removeFilm(listFilmDto, 1) }
                    )
                    listSimilarMovies.adapter = adapterSimilarMovies
                    detailSimilarMovies.counter.text = similarMovies.size.toString()
                    detailViewModel.viewPeople(detailSimilarMovies, similarMovies.size)
                }
                if (gallery.isEmpty()) {
                    listGallery.visibility = View.GONE
                    detailGallery.mainItemFilmWasFilmed.visibility = View.GONE
                } else {
                    val adapterGallery = AdapterGallery({ position, list ->
                        viewModel.photoDetail(
                            position,
                            findNavController().navigate(R.id.action_detailFilmFragment_to_galleryPhoto),
                            list
                        )
                    }, gallery)
                    listGallery.adapter = adapterGallery
                    detailGallery.counter.text = gallery.size.toString()
                    viewModel.listPhotoGallery[viewModel.counterBottomNavigation] = gallery
                    detailViewModel.viewPeople(detailGallery, gallery.size)
                }

                if (similarMovies.isEmpty()) {
                    listSimilarMovies.visibility = View.GONE
                    detailSimilarMovies.mainItemFilmWasFilmed.visibility = View.GONE
                } else {
                    val adapterSimilarMovies = AdapterFilmPremieres(
                        profileViewModule,
                        similarMovies,
                        viewModel,
                        { allCollection() },
                        { id -> detailFilm(id) },
                        { listFilmDto -> profileViewModule.addFim(listFilmDto, 1) },
                        { listFilmDto -> profileViewModule.removeFilm(listFilmDto, 1) }
                    )
                    listSimilarMovies.adapter = adapterSimilarMovies
                    detailSimilarMovies.counter.text = similarMovies.size.toString()
                    detailViewModel.viewPeople(detailSimilarMovies, similarMovies.size)
                }
            }
            profileViewModule.addFim(viewModel.formatListFilm(detailName), 2)
        }

    }

    private fun detailFilm(id: Int) {
        viewModel.idFilm[viewModel.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_detailFilmFragment_self)
    }

    private fun person(id: Int) {
        viewModel.idPerson[viewModel.counterBottomNavigation]!!.add(id)
        findNavController().navigate(R.id.action_detailFilmFragment_to_personFragment)
    }

    private fun allCollection() {
        viewModel.nameCollection[viewModel.counterBottomNavigation]!!
            .add(getString(R.string.similar_films))
        viewModel.listFilm[viewModel.counterBottomNavigation]!!.add(similarMovies)
        findNavController().navigate(R.id.action_detailFilmFragment_to_collectionFilm)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        jobRequest.cancel()
        _binding = null
    }

}