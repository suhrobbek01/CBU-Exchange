package com.example.cbuexchange

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.cbuexchange.utils.MyContextWrapper
import com.example.cbuexchange.utils.MySharedPreference
import java.util.Locale


class MainActivity2 : AppCompatActivity() {
    lateinit var mySharedPreference: MySharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }

    override fun attachBaseContext(context: Context) {
        mySharedPreference = MySharedPreference(context)
        // adjust the name of the SharedPreference to yours
        val language: String? = mySharedPreference.getPreference()
        super.attachBaseContext(MyContextWrapper.wrap(context, language.toString()))
        val locale = Locale(language.toString())
        val resources = baseContext.resources
        val conf: Configuration = resources.configuration
        conf.locale = locale
        resources.updateConfiguration(conf, resources.displayMetrics)
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp()
    }
}