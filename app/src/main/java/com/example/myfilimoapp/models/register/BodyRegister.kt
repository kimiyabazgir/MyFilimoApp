package com.example.myfilimoapp.models.register

import com.google.gson.annotations.SerializedName

data class BodyRegister(
    //باید var تعریف کنیم هر دیتا رو چون اینجا مقدار دیفالت میدیم بعدا توو صفحه register از کاربر مقدار می گیریم و set میکنیم
    @SerializedName("name")
    var name: String="",
    @SerializedName("email")
    var email:String="",
    @SerializedName("password")
    var password: String=""
)
