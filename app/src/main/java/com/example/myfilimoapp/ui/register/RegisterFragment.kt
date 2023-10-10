package com.example.myfilimoapp.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.myfilimoapp.R
import com.example.myfilimoapp.databinding.FragmentRegisterBinding
import com.example.myfilimoapp.databinding.FragmentSplashBinding
import com.example.myfilimoapp.models.register.BodyRegister
import com.example.myfilimoapp.utils.StoreUserData
import com.example.myfilimoapp.utils.showInvisible
import com.example.myfilimoapp.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

private lateinit var binding: FragmentRegisterBinding

//برای دسترسی به متدهای getUserToken وsaveUserToken میایم
@Inject
 lateinit var userData: StoreUserData
 //تعریف و معرفی view model به صفحه fragment register
 private val viewModel:RegisterViewModel by viewModels()
    //چون در صورت خالی نبودن فیلد ها باید اطلاعات وارده توسط کاربر را در body ذخیره کنم پس میام body رو هم توو این صفحه تعریف می کنم
//بهتره بیای bodyRegister رو تزریق کنی حالا چون دیتاکلاسه باید بری جدا براش یه ماژول بنویسی و اون ماژول رو تزریق کنی
   // private lateinit var body: BodyRegister
    //حالا که براش ماژول نوشتیم بجای خط بالا میایم تزریقش می کنیم به صفحه مون body رو
    @Inject
    lateinit var body: BodyRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
binding=FragmentRegisterBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //گرفتن اطلاعات از کاربر
            submitBtn.setOnClickListener{view->
            val username=usernameEdt.text.toString()
            val email=emailEdt.text.toString()
            val password=passwordEdt.text.toString()
                //Validation
                //چک میکنه ببینه فیلد ها هرکدوم پر اند یا نه
                //اینکه گفتیم سه تا فیلد خالی نباشه یعنی مث اجباری باهاش رفتار کن
                if (username.isNotEmpty()||email.isNotEmpty()||password.isNotEmpty()){
                    //با زدن کلیک این سه تا رو که از کاربر گرفتیم میگیره توو body ذخیره میکنه
                    body.name=username
                    body.email=email
                    body.password=password

                }
                //توو else میگم اگه خالی بود بیا خطا نشون بده
                else{
                    Snackbar.make(view,getString(R.string.fillAllFields),Snackbar.LENGTH_SHORT).show()

                }

                //فرستادن اطلاعات body با استفاده از view model
                //Send Data
                viewModel.sendRegisterUser(body)
                //Loading
                //در صورتیکه lifecycle این activity که تووشیم در دسترس باشهmutable livedata نشون دادن یا ندادن لودینگ رو بفرسته به observe
                //وقتی viewLifecycleOwner رو میدیم به observe داریم میگیم اونایی که lifecycle شون در دسترسه
                viewModel.loading.observe(viewLifecycleOwner){isShown->
                    if(isShown){
                        //اومدیم یه اکستنشن فانکشن برای ویو هامون نوشتیم که visible بودنش رو با یه booloean به هرکدوم از ویو هامون که میخواهیم بدیم بجای نوشتن خط پایین از این متد استفاده کنیم
                      //submitLoading.visibility=View.VISIBLE
                        submitLoading.showInvisible(true)
                        submitBtn.showInvisible(false)
                      }
                    else{
                        submitLoading.showInvisible(true)
                        submitBtn.showInvisible(false)
                    }
                 }
                viewModel.registerUser.observe(viewLifecycleOwner){response->
                    //  اینجا میگیم اگر ثبت نام با موفقیت انجام شد بیا اطلاعاتو برا من ذخیره کن اطلاعات توو ذخیره می شد userData که یه دیتا استوره
                    lifecycle.coroutineScope.launchWhenCreated {
                        //  نname رو ذخیره کردم برای توکن بودن
                        userData.saveUserToken(response.name.toString())
                    }

                    }
                }
}
}
}

