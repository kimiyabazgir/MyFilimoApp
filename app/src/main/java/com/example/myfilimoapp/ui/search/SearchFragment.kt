package com.example.myfilimoapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfilimoapp.R
import com.example.myfilimoapp.databinding.FragmentSearchBinding
import com.example.myfilimoapp.ui.home.adapters.LastMoviesAdapter
import com.example.myfilimoapp.utils.initRecycler
import com.example.myfilimoapp.utils.showInvisible
import com.example.myfilimoapp.viewmodel.SearchViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var searchAdapter: LastMoviesAdapter

    private val viewmodel: SearchViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //Search
            searchEdt.addTextChangedListener {
                val search = it.toString()
                if (search.isNotEmpty()) {
                    viewmodel.loadSearchMovies(search)
                }
            }
            //Get movies list e bad az search
            viewmodel.moviesList.observe(viewLifecycleOwner) {
                //por kardane adapter
                searchAdapter.setData(it.data)
                //por kardan(initialize kardane) recyclerView
                moviesRecycler.initRecycler(LinearLayoutManager(requireContext()), searchAdapter)
            }
            //neshoon dadane loading
            viewmodel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    searchLoading.showInvisible(true)
                } else {
                    searchLoading.showInvisible(false)
                }
            }
            searchAdapter.SetOnItemClickListener {
                val direction=SearchFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }

            //neshoon dadane emptyList
            viewmodel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    moviesRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    moviesRecycler.showInvisible(true)
                }
            }

        }
    }
}

