package com.example.myfilimoapp.utils

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

//این اکستنشن فانکشن روروی کلاس ویو نوشتم که روی همه ویو هام بتونم اعمالش کنم
fun View.showInvisible(isShown:Boolean){
    if(isShown){
        this.visibility=View.VISIBLE
    }
    else
    {
        this.visibility=View.INVISIBLE
    }
}
//این متد ریسایکلرویوت رو برات تعریف می کنه
fun RecyclerView.initRecycler(layoutManager: RecyclerView.LayoutManager,adapter: RecyclerView.Adapter<*>){
    //یه کد کلی مینویسم که با هر اداپتر با هر ویومدل و هر لایوت منیجری رو ساپورت کنه
    //*یعنی هر اداپتر با هر ویوهولدری که قرار داد تو بیا ازش استفاده کن
    this.layoutManager=layoutManager
    this.adapter=adapter
    //این پایینیا اختیاری میتونی بذاری که در صورت استفاده از این اکستنشن فانکشنت برای ریسایکلرویو مورد نظر اعمال شود
   /* this.setHasFixedSize(true)
    this.animation()
    */
}
