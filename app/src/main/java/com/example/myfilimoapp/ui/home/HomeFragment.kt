package com.example.myfilimoapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.myfilimoapp.R
import com.example.myfilimoapp.databinding.FragmentHomeBinding
import com.example.myfilimoapp.ui.home.adapters.GenresAdapter
import com.example.myfilimoapp.ui.home.adapters.LastMoviesAdapter
import com.example.myfilimoapp.ui.home.adapters.TopMoviesAdapter
import com.example.myfilimoapp.utils.initRecycler
import com.example.myfilimoapp.utils.showInvisible
import com.example.myfilimoapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    //تزریق adapter
    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    @Inject
    lateinit var genresAdapter: GenresAdapter

    @Inject
    lateinit var lastMoviesAdapter: LastMoviesAdapter

    //تعریف viewmodel
    private val viewModel: HomeViewModel by viewModels()
    private val pagerHelper: PagerSnapHelper by lazy { PagerSnapHelper() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //call api
        viewModel.loadTopMoviesList(3)
        viewModel.loadGenresList()
        viewModel.loadLastMoviesList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //دریافت اطلاعات از بکند بعد از call api
            //Get Top Movies
            viewModel.topMoviesList.observe(viewLifecycleOwner) {
                //این الان response اون api لیست فیلمارو بهم میده بخاطر لایو دیتا بودن هم یه observer براش میذارم که حواسش باشه موقع تغییر اپدیت کنه
                topMoviesAdapter.differ.submitList(it.data) {
                    //حالا این اطلاعاتو از response ای که برگشت میگیرم میریزم توو recycler view
                    //برای این کار باید Recyclerview رو تعریف میکردیم بعد layout manager رو براش set می کردیم
                    //اما چون در طول پروژه خیلی از Recycler استفاده کردیم میایم بصورت یه extention function می نویسیم و هرجا لازم داشتیم استفاده میکنیم
                    //RecyclerView
                    topMoviesRecycler.initRecycler(
                        LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        ), topMoviesAdapter
                    )
                    //حالا ایندیکیتور رو وصل میکنیم به ریسایکلر ویومون و با pagersnaphelperمیگیم کاربر با هر اسکرول بتونه فقط یک ایتیم جابجا کنه نتونه نصفه ایتیم وایسه
                    //Indicator
                    pagerHelper.attachToRecyclerView(topMoviesRecycler)
                    topmoviesIndicator.attachToRecyclerView(topMoviesRecycler, pagerHelper)
                }
            }
            //Get Genres
            viewModel.genresList.observe(viewLifecycleOwner) {
                genresAdapter.differ.submitList(it)
                //حالاinitialize کردن recyclerview مون
                //چون قبلا init recycler رو نوشتیم اداپترمونو بهش می دیم خودش میره ست میکنه اداپترو
                genresRecycler.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    genresAdapter
                )
            }
            //Get last Movies
            viewModel.lastMoviesList.observe(viewLifecycleOwner) {
                lastMoviesAdapter.setData(it.data)
                //RecyclerView
                lastMoviesRecycler.initRecycler(
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    ), lastMoviesAdapter)
            }
            //***
            //میگم اومد کلیک کرد روو یه مورد از اداپتور حالا که یه کلیکی نوشتم که دیتارو میگیره میبره هرجا بخوای بیا idرو بگیر و ببر به فلان صفحه
          lastMoviesAdapter.SetOnItemClickListener {
              val direction=HomeFragmentDirections.actionToDetail(it.id!!.toInt())
              findNavController().navigate(direction)
          }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    moviesLoading.showInvisible(true)
                    moviesScrollLay.showInvisible(false)

                } else {
                    moviesLoading.showInvisible(false)
                    moviesScrollLay.showInvisible(true)
                }
            }
        }
    }
}