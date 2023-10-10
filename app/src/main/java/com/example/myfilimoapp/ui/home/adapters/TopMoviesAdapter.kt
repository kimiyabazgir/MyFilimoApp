package com.example.myfilimoapp.ui.home.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myfilimoapp.databinding.FragmentHomeBinding
import com.example.myfilimoapp.databinding.ItemHomeMoviesTopBinding
import com.example.myfilimoapp.models.home.ResponseMoviesList
import javax.inject.Inject

class TopMoviesAdapter @Inject constructor() : RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>(){
private lateinit var binding: ItemHomeMoviesTopBinding
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):TopMoviesAdapter.ViewHolder {
        binding=ItemHomeMoviesTopBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return ViewHolder()
    }

    override fun onBindViewHolder(holder:TopMoviesAdapter.ViewHolder,position:Int) {
        holder.setData(differ.currentList[position])
        //این برای duplicate نشدن آیتم های لیستته
        holder.setIsRecyclable(false)
    }
//اینجوری میگم فقظ 5تا نشون بده
    override fun getItemCount()=5

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){
        fun setData(item:ResponseMoviesList.Data){
            binding.apply {
                movieNameTxt.text=item.title
                movieInfoTxt.text="${item.imdb_rating} | ${item.country} |${item.year}"
                moviePosterImg.load(item.poster){
                    crossfade(true)
                    crossfade(800)
                }
            }

        }

    }
    private val differCallback = object : DiffUtil.ItemCallback<ResponseMoviesList.Data>(){

        override fun areItemsTheSame(oldItem: ResponseMoviesList.Data, newItem: ResponseMoviesList.Data): Boolean {
        return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseMoviesList.Data, newItem: ResponseMoviesList.Data): Boolean {
        return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,differCallback)
}