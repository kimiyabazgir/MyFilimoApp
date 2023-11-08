package com.example.myfilimoapp.ui.detail
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myfilimoapp.databinding.ItemDetailImagesBinding
import com.example.myfilimoapp.databinding.ItemHomeGenreListBinding
import com.example.myfilimoapp.models.ResponseGenresList
import javax.inject.Inject

class ImagesAdapter @Inject constructor() : RecyclerView.Adapter<ImagesAdapter.ViewHolder>(){
private lateinit var binding: ItemDetailImagesBinding
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):ImagesAdapter.ViewHolder {
        binding=ItemDetailImagesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return ViewHolder()
    }

    override fun onBindViewHolder(holder:ImagesAdapter.ViewHolder, position:Int) {
        holder.setData(differ.currentList[position])
        //این برای duplicate نشدن آیتم های لیستته
        holder.setIsRecyclable(false)
    }
//اینجوری میگم هرچندتا که نیازه نشون بده
    override fun getItemCount()=differ.currentList.size

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){
        //اگه responseDetail رو ببینی images داره لیستی از string ها رو به ما میده val images: List<String پس در اینجا هم باید به load همینو بدیم
        //برای همین item رو String قرار دادیم
        fun setData(item:String){
            binding.apply {
                itemImages.load(item){
                    crossfade(true)
                    crossfade(800)
                }
                }
            }
        }
    private val differCallback = object : DiffUtil.ItemCallback<String>(){

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem:String, newItem: String): Boolean {
        return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,differCallback)
}
