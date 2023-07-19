package com.example.cbuexchange.ui2

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.blongho.country_data.World
import com.example.cbuexchange.R
import com.example.cbuexchange.databinding.CountDialogLayoutBinding
import com.example.cbuexchange.entity.Currency
import com.example.cbuexchange.utils.getCurrentLocale
import java.util.Locale

class MyCustomCalculatorDialog(private var currency: Currency, private val context: Context) :
    DialogFragment() {
    private lateinit var binding: CountDialogLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CountDialogLayoutBinding.inflate(inflater, container, false)
        val resources: Resources = context.resources
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        binding.apply {
            val currentLocale = getCurrentLocale(context)
            val language = currentLocale?.language
            currencyName2.text = resources.getString(R.string.dialog_text2)
            when (language) {
                "en" -> {
                    currencyName1.text = currency.ccy_name_en
                }

                "ru" -> {
                    currencyName1.text = currency.ccy_name_ru
                }

                "uz" -> {
                    currencyName1.text = currency.ccy_name_uz
                }
            }
            currency1.text = currency.ccy
            flagCurrency1.setImageResource(World.getFlagOf(currency.code))
            exchangeButton.setOnClickListener {
                val trim = currency1Type.text.toString().trim()
                if (trim.isNotEmpty()) {
                    answer.text = (currency1Type.text.toString().trim()

                        .toDouble() * currency.rate.toDouble()).toString()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "You should input the value of currency!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}