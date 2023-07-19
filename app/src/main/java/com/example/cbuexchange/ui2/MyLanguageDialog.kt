package com.example.cbuexchange.ui2

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.cbuexchange.MainActivity2
import com.example.cbuexchange.R
import com.example.cbuexchange.databinding.LanguageDialogLayoutBinding
import com.example.cbuexchange.utils.LocaleHelper
import com.example.cbuexchange.utils.MySharedPreference
import com.example.cbuexchange.utils.getCurrentLocale

class MyLanguageDialog(val context1: Context) : DialogFragment() {
    private lateinit var binding: LanguageDialogLayoutBinding
    lateinit var mySharedPreference: MySharedPreference
    private lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LanguageDialogLayoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val currentLocale = getCurrentLocale(context1)
        val language = currentLocale?.language
        mySharedPreference = MySharedPreference(requireContext())
        binding.apply {
            language1Layout.setOnClickListener {
                isSelected3.visibility = View.GONE
                isSelected2.visibility = View.GONE
                isSelected.visibility = View.VISIBLE
                context = LocaleHelper().setLocale(context1, "uz")
                val resources: Resources = context1.resources
                selectLanguage.text = resources.getString(R.string.select_language)
                refresh()

            }
            language2Layout.setOnClickListener {
                isSelected.visibility = View.GONE
                isSelected2.visibility = View.VISIBLE
                isSelected3.visibility = View.GONE
                context = LocaleHelper().setLocale(context1, "ru")
                refresh()
            }
            language3Layout.setOnClickListener {
                isSelected3.visibility = View.VISIBLE
                isSelected2.visibility = View.GONE
                isSelected.visibility = View.GONE
                context = LocaleHelper().setLocale(context1, "en")
                refresh()
            }

            when (language) {
                "en" -> {
                    isSelected.visibility = View.GONE
                    isSelected2.visibility = View.GONE
                    isSelected3.visibility = View.VISIBLE
                }

                "ru" -> {
                    isSelected.visibility = View.GONE
                    isSelected2.visibility = View.VISIBLE
                    isSelected3.visibility = View.GONE
                }

                "uz" -> {
                    isSelected.visibility = View.VISIBLE
                    isSelected2.visibility = View.GONE
                    isSelected3.visibility = View.GONE
                }
            }
        }
        return binding.root
    }

    private fun refresh() {
        val refresh = Intent(requireActivity(), MainActivity2::class.java)
        requireActivity().finish()
        startActivity(refresh)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}