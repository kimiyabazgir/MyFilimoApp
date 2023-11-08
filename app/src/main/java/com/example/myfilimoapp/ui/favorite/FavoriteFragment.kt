package com.example.myfilimoapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfilimoapp.databinding.FragmentFavoriteBinding
import com.example.myfilimoapp.utils.initRecycler
import com.example.myfilimoapp.utils.showInvisible
import com.example.myfilimoapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel:FavoriteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {
            //show all favorites
            //انگار داریم api کال می کنیم میگم بیا هرچی favorite دارم رو نشون بده
            viewModel.loadFavoriteList()
            //حالا میام از observer ها استفاده می کنم و می گم اگر لیست دارم اون اداپتور فیوریت هامو اپدیت کن اگر ندارم اون empty رو بهم نشون بده
            viewModel.favoriteList.observe(viewLifecycleOwner){
                favoriteAdapter.setData(it)
                favoriteRecycler.initRecycler(LinearLayoutManager(requireContext()),favoriteAdapter)
            }
            favoriteAdapter.setOnItemClickListener {
                val direction=FavoriteFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }
            //حالت خالی بودن لیست favorite
            //neshoon dadane emptyList
            viewModel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    favoriteRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    favoriteRecycler.showInvisible(true)
                }
                }
        }
    }
}