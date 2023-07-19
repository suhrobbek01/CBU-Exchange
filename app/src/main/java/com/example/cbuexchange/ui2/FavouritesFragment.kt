package com.example.cbuexchange.ui2

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.cbuexchange.R
import com.example.cbuexchange.database.AppDatabase
import com.example.cbuexchange.databinding.FragmentFavouritesBinding
import com.example.cbuexchange.entity.Currency
import com.example.cbuexchange.ui2.adapters.ExchangesAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private val binding by viewBinding(FragmentFavouritesBinding::bind)
    lateinit var appDatabase: AppDatabase
    lateinit var adapter: ExchangesAdapter
    lateinit var currencies: ArrayList<Currency>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Favourites")
    }

    override fun onResume() {
        super.onResume()
        currencies = appDatabase.currencyDao().getAllCurrencies() as ArrayList<Currency>
        val list = currencies.filter {
            it.status == 1
        } as ArrayList
        adapter =
            ExchangesAdapter(requireContext(), list, object : ExchangesAdapter.OnClickListener {
                override fun onClickListener(data: Currency) {
                    MyCustomCalculatorDialog(data, requireContext()).show(
                        requireActivity().supportFragmentManager,
                        "MyCustomFragment"
                    )
                }

                override fun onStarClickListener(
                    imageView: ImageView, position: Int, currency: Currency
                ) {
                    currency.status = 0
                    appDatabase.currencyDao().updateCurrency(currency)
                    list.remove(currency)
                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, currencies.size - 1)
                }
            })
        binding.rv.adapter = adapter

        Log.d(TAG, "onResume: Favourites")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabase = AppDatabase.getInstance(requireContext())
        Log.d(TAG, "onViewCreated: Favourites")
        currencies = appDatabase.currencyDao().getAllCurrencies() as ArrayList<Currency>
        val list = currencies.filter {
            it.status == 1
        } as ArrayList
        adapter =
            ExchangesAdapter(requireContext(), list, object : ExchangesAdapter.OnClickListener {
                override fun onClickListener(data: Currency) {
                    MyCustomCalculatorDialog(data, requireContext()).show(
                        requireActivity().supportFragmentManager,
                        "MyCustomFragment"
                    )
                }

                override fun onStarClickListener(
                    imageView: ImageView, position: Int, currency: Currency
                ) {
                    currency.status = 0
                    appDatabase.currencyDao().updateCurrency(currency)
                    list.remove(currency)
                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, currencies.size - 1)
                }
            })
        binding.rv.adapter = adapter
    }
}