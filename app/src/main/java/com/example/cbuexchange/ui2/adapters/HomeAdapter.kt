package com.example.cbuexchange.ui2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cbuexchange.ui2.ExchangeRatesFragment
import com.example.cbuexchange.ui2.FavouritesFragment

class HomeAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return ExchangeRatesFragment()
            }

            else -> {
                return FavouritesFragment()
            }
        }
    }
}