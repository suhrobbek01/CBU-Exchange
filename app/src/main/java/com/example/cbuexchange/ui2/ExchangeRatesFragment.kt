package com.example.cbuexchange.ui2

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.blongho.country_data.World
import com.example.cbuexchange.NetworkHelper
import com.example.cbuexchange.R
import com.example.cbuexchange.database.AppDatabase
import com.example.cbuexchange.databinding.FragmentExchangeRatesBinding
import com.example.cbuexchange.entity.Currency
import com.example.cbuexchange.models.Data
import com.example.cbuexchange.ui2.adapters.ExchangesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ExchangeRatesFragment : Fragment(R.layout.fragment_exchange_rates) {
    private val binding by viewBinding(FragmentExchangeRatesBinding::bind)
    private lateinit var adapter: ExchangesAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var requestQueue: RequestQueue
    var listData: List<Data>? = null
    lateinit var networkHelper: NetworkHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Exchange Rates")
        World.init(requireContext().applicationContext)
    }

    override fun onResume() {
        super.onResume()
        networkHelper = NetworkHelper(requireContext())
        if (networkHelper.isNetworkConnected()) {
            getData()
        } else {
            val listData = appDatabase.currencyDao().getAllCurrencies()
            setData(listData)
        }
        Log.d(TAG, "onResume: Exchange Rates")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Exchange Rates")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Exchange Rates")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabase = AppDatabase.getInstance(requireContext())
        Log.d(TAG, "onViewCreated: Exchange Rates")

        networkHelper = NetworkHelper(requireContext())
        if (networkHelper.isNetworkConnected()) {
            getData()
        } else {
            val listData = appDatabase.currencyDao().getAllCurrencies()
            setData(listData)
        }
        Log.d(TAG, "onViewCreated:${listData} ")
    }

    fun setData(list: List<Currency>) {
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
                    when (currency.status) {
                        0 -> {
                            currency.status = 1
                        }

                        1 -> {
                            currency.status = 0
                        }
                    }
                    appDatabase.currencyDao().updateCurrency(currency)
                    adapter.notifyItemChanged(position)
                }
            })
        binding.apply {
            recV.adapter = adapter
        }
    }

    private fun getData(): List<Data>? {
        requestQueue = Volley.newRequestQueue(requireContext())

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            "http://cbu.uz/uzc/arkhiv-kursov-valyut/json/",
            null,
            { response ->
                val str = response.toString()
                val type = object : TypeToken<List<Data>>() {}.type
                listData = ArrayList()
                listData = Gson().fromJson(str, type) as ArrayList<Data>
                val list = ArrayList<Currency>()
                writeToDatabase(list = list)
                setData(list)
                Log.d(TAG, "onResponse: ${listData}")
            }) { error ->
            Log.d(TAG, "onErrorResponse: $error")
        }
        requestQueue.add(jsonArrayRequest)
        return listData
    }

    private fun writeToDatabase(list: ArrayList<Currency>) {
        Log.d(TAG, "getData: ${listData}")
        val allCurrencies = appDatabase.currencyDao().getAllCurrencies()
        if (allCurrencies.isNotEmpty()) {
            val filter = allCurrencies.filter {
                it.status == 1
            }
            listData?.forEach {
                var check = false
                filter.forEach { i ->
                    if (it.Code == i.code) {
                        list.add(i)
                        check = true
                    }
                }
                if (!check) {
                    list.add(
                        Currency(
                            ccy = it.Ccy,
                            ccy_name_en = it.CcyNm_EN,
                            ccy_name_ru = it.CcyNm_RU,
                            ccy_name_uz = it.CcyNm_UZ,
                            code = it.Code,
                            date = it.Date,
                            rate = it.Rate,
                            status = 0,
                            currency_id = it.id
                        )
                    )
                }
            }
        } else {
            (listData as ArrayList<Data>).forEach { it1 ->
                list.add(
                    Currency(
                        ccy = it1.Ccy,
                        ccy_name_en = it1.CcyNm_EN,
                        ccy_name_ru = it1.CcyNm_RU,
                        ccy_name_uz = it1.CcyNm_UZ,
                        code = it1.Code,
                        date = it1.Date,
                        rate = it1.Rate,
                        status = 0,
                        currency_id = it1.id
                    )
                )
            }
        }
        appDatabase.currencyDao().addCurrencyList(list)
    }
}