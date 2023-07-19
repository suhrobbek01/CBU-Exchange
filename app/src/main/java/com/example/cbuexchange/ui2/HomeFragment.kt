package com.example.cbuexchange.ui2

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.cbuexchange.R
import com.example.cbuexchange.database.AppDatabase
import com.example.cbuexchange.databinding.FragmentHomeBinding
import com.example.cbuexchange.ui2.adapters.HomeAdapter
import com.example.cbuexchange.utils.MySharedPreference
import com.example.cbuexchange.utils.getCurrentLocale
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.util.Locale

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var homeAdapter: HomeAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var mySharedPreference: MySharedPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabase = AppDatabase.getInstance(requireContext())
        mySharedPreference = MySharedPreference(requireContext())

        homeAdapter = HomeAdapter(requireActivity())
        binding.viewPager.adapter = homeAdapter
        val nightModeFlags =
            context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.dayNightMode.setImageResource(R.drawable.day_moon)
                mySharedPreference.setDarkMode("dark", "dark_mode")
            }

            Configuration.UI_MODE_NIGHT_NO -> {
                binding.dayNightMode.setImageResource(R.drawable.night_moon)
                mySharedPreference.setDarkMode("dark", "night_mode")
            }

            Configuration.UI_MODE_NIGHT_UNDEFINED -> {

            }
        }

        val currentLocale = getCurrentLocale(requireContext())
        val language = currentLocale?.language
        when (language) {
            "en" -> {
                binding.languageFlag.setImageResource(R.drawable.usa_flag)
                mySharedPreference.setPreferences("Locale.Helper.Selected.Language", "en")
            }

            "ru" -> {
                binding.languageFlag.setImageResource(R.drawable.russian_flag)
                mySharedPreference.setPreferences("Locale.Helper.Selected.Language", "ru")
            }

            "uz" -> {
                binding.languageFlag.setImageResource(R.drawable.uzbekistan_flag)
                mySharedPreference.setPreferences("Locale.Helper.Selected.Language", "uz")
            }
        }

        val isDarkMode = mySharedPreference.getDarkMode("dark")
        binding.dayNightBtn.setOnClickListener {
            if (isDarkMode == "dark_mode") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mySharedPreference.setDarkMode("dark", "night_mode")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mySharedPreference.setDarkMode("dark", "dark_mode")
            }
        }
        val resources: Resources = requireContext().resources
        binding.languageText.text = resources.getString(R.string.language)
        binding.languageLayout.setOnClickListener {
            val langDialog = MyLanguageDialog(requireContext())
            langDialog.show(
                requireActivity().supportFragmentManager, "MyCustomFragment"
            )
        }
        TabLayoutMediator(
            binding.tablayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.setText(resources.getString(R.string.tab1))
                }

                1 -> {
                    tab.setText(resources.getString(R.string.tab2))
                }
            }
        }.attach()
    }
}