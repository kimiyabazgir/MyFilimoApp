package com.example.myfilimoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myfilimoapp.R
import com.example.myfilimoapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
//همه fragment هامون دارن از hilt استفاده می کنن اگه parent شون که activity هست هم باید گرافhilt رو بشناسه وگرنه eror میگیریم
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //تعریف navigation
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            //تعریف navigation
            navController=findNavController(R.id.navHost)
            //وصل کردن bottom Navigation به navController
        bottomNav.setupWithNavController(navController)
            //میام میگم صفحه هایی که رفتی اگر بجز صفحه splash وregister وdetail بود نشون بده باتم نویگیشنو تووشون
            //در واقع یعنی نشون نده توو زیر این صفحات باتم نویگیشنو
        navController.addOnDestinationChangedListener{_,destination,_ ->
           if (destination.id==R.id.splashFragment || destination.id==R.id.registerFragment || destination.id==R.id.detailFragment){
               bottomNav.visibility=View.GONE
           }else{
               bottomNav.visibility=View.VISIBLE
           }
        }
        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()||super.onNavigateUp()
    }
}