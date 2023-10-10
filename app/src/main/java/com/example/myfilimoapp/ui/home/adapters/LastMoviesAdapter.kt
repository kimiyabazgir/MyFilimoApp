package com.example.myfilimoapp.ui.home.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myfilimoapp.databinding.FragmentHomeBinding
import com.example.myfilimoapp.databinding.ItemHomeMoviesLastBinding
import com.example.myfilimoapp.databinding.ItemHomeMoviesTopBinding
import com.example.myfilimoapp.models.home.ResponseMoviesList
import javax.inject.Inject

class LastMoviesAdapter @Inject constructor() : RecyclerView.Adapter<LastMoviesAdapter.ViewHolder>(){

private lateinit var binding: ItemHomeMoviesLastBinding
private var moviesList= emptyList<ResponseMoviesList.Data>()

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):LastMoviesAdapter.ViewHolder {
        binding=ItemHomeMoviesLastBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return ViewHolder()
    }

    override fun onBindViewHolder(holder:LastMoviesAdapter.ViewHolder, position:Int) {
        holder.setData(moviesList[position])
        //این برای duplicate نشدن آیتم های لیستته
        holder.setIsRecyclable(false)
    }
//اینجوری میگم فقظ 5تا نشون بده
    override fun getItemCount()=moviesList.size

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){
        fun setData(item:ResponseMoviesList.Data){
            binding.apply {
               /* movieNameTxt.text=item.title
                movieInfoTxt.text="${item.imdb_rating} | ${item.country} |${item.year}"
                moviePosterImg.load(item.poster){
                    crossfade(true)
                    crossfade(800)
                }*/
            }

        }

    }

    fun setData(data:List<ResponseMoviesList.Data>){
        val moviesDiffUtils=MoviesDiffUtils(moviesList,data)
        val diffUtils=DiffUtil.calculateDiff(moviesDiffUtils)
        //میگم اطلاعاتو گرفتی بریز توو movieslist
        moviesList=data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(private val oldItem:List<ResponseMoviesList.Data>,private val newItem:List<ResponseMoviesList.Data>) : DiffUtil.Callback(){

        override fun getOldListSize(): Int {
            return oldItem.size
        }


        override fun getNewListSize(): Int {
           return newItem.size
        }

//این میاد شماره آیتم ها رو چک میکنه ببینه درسته شماره ایتم جدیده که اومده با قبلی
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem [oldItemPosition]=== newItem[newItemPosition]
        }

       //اگر بالایی true بود میاد اینو چک می کنه ببینه محتوای این ایتمه تغییر میکنه یعنی محتوا جدیده با قبلیه یکیه یا بیاد جایگزین کنه اگر بالایی false بود نمیاد این متدو چک کنه
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem [oldItemPosition]=== newItem[newItemPosition]
        }
        //در = = فقط value چک میشه اما در = = = هم value هم data type دو طرف چک میشه
    }
}