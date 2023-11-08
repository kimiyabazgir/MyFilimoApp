package com.example.myfilimoapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.myfilimoapp.R
import com.example.myfilimoapp.databinding.FragmentDetailBinding
import com.example.myfilimoapp.db.MovieEntity
import com.example.myfilimoapp.utils.initRecycler
import com.example.myfilimoapp.utils.showInvisible
import com.example.myfilimoapp.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    @Inject
    lateinit var entity: MovieEntity
    private var movieID = 0
    private val viewModel: DetailViewModel by viewModels()

    //اینم برای آرگیومنتمون که منتقل میکنه movieID رو از صفحات مختلف به این صفحه
    private val args: DetailFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get data
        movieID = args.movieID
        //Call api
        if (movieID > 0) {
            viewModel.loadDetailMovie(movieID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            //Load data
            viewModel.detailMovie.observe(viewLifecycleOwner) { response ->
                posterBigImage.load(response.poster)
                posterNormalImage.load(response.poster) {
                    crossfade(true)
                    crossfade(800)
                }
                movieNameTxt.text = response.title
                movieRateTxt.text = response.imdb_rating
                movieTimeTxt.text = response.runtime
                movieDateTxt.text = response.released
                movieActorsInfo.text = response.actors
                movieSummaryInfo.text = response.plot
                //ImagesAdapter
                imagesAdapter.differ.submitList(response.images)
                imagesRecyclerView.initRecycler(
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    ), imagesAdapter
                )
                //Fav Click
                //اگه بیاد روی دکمه favorite توی صفحه detail کلیک کنه بیا این اطلاعاتشو که تو صفحه favorite نشون میدیم بگیر
                favImg.setOnClickListener {
                    entity.id = movieID
                    entity.poster = response.poster.toString()
                    entity.title = response.title.toString()
                    entity.rate = response.rated.toString()
                    entity.country = response.country.toString()
                    entity.year = response.year.toString()
                    viewModel.favoriteMovie(movieID, entity)
                }
            }

            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    detailLoading.showInvisible(true)
                    detailSchrollView.showInvisible(false)
                } else {
                    detailLoading.showInvisible(false)
                    detailSchrollView.showInvisible(true)
                }
            }

            //Defult Fav icon color
            //متد existsMovie چون یه suspendFunction است باید توو یه suspendFunction یا croutineScope استفاده بشه
            //این برا حالتیه که وارد صفحه جزییات میشه ببینه فیلمی که داره جزییاتشو میبینه فیوریت شده یا نه
            lifecycleScope.launchWhenCreated {
                if (viewModel.existsMovie(movieID)) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                }
            }

            //Back
            backImg.setOnClickListener {
                findNavController().navigateUp()
            }

            //این برا حالتیه که توو صفحه جزییاته میاد همونجا فیلمی که داره جزییاتشو میبینه فیوریت میکنه میخواهیم تغییرات اون قلبه رو همون جا هم ببینه دیگه
            viewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                }
            }
        }
        //فرق navigateUp و popStack
        //در popStack میتونیم چندتا صفحه رو ببندیمو از صفحه استک حذف کنیم و مشخص کنیم که تا چه صفحه ای دقیقا صفحه ها بسته شوند
        //معمولا برای بک های روی toolbar مثل همینجا از navigateUp استفاده می کنیم
    }
}