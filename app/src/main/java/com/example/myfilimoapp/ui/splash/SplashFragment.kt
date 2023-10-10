package com.example.myfilimoapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.myfilimoapp.R
import com.example.myfilimoapp.databinding.FragmentSplashBinding
import com.example.myfilimoapp.utils.StoreUserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding= FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    //تزریق dataStore به صفحه splash برای چک کردن اینکه کاربر با توجه به داشتن یا نداشتن توکن ثبت نام شده یا نه بره به صفحه register
    //*
    @Inject
    lateinit var storeUserData: StoreUserData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //حالا میخوام با کروتین یه دیلی بندازم برا کاریر که دوثانیه توو صفحه splash بمونه و بعد از دوثانیه چک کنه کاربر ثبت نام کرده یا نه لوگو رو ببینه بعد هدایت شه صفحه بعد
        lifecycle.coroutineScope.launchWhenCreated {
            delay(2000)
            //check user token
            //الان با collect میایم اطلاعات اپدیت شده ی کلاس storeUserData رو با متد fun getUserToken می گیریم که کلاس  storeUserData رو اینجا * تزریق کردیم
            // از اونجا که متد getUserToken یه string به ما برمی گردونه که توی userToken ذخیره شده میایم چک میکنیم اگر فیلد it که اینجا داره اون string رو برمیگردونه خالی بود و چیزی تووش ذخیره نشده بود یعنی کاربر ثبت نام نکرده و باید action رفتن به صفحه register اتفاق بیفته
            //وگرنه ببر توو صفحه home
            // با collect به محض آپدیت اطلاعات مربوط به ثبت نام کاربر در صفحه رجیستر یا هر صفحه ای
            storeUserData.getUserToken().collect() {
                if (it.isEmpty()) {
                    findNavController().navigate(R.id.actionSplashToRegister)
                } else {
                    findNavController().navigate(R.id.actionToHome)
                }
            }
        }
    }
}
