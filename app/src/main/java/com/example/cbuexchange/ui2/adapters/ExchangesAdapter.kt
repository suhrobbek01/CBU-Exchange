package com.example.cbuexchange.ui2.adapters

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.World
import com.example.cbuexchange.R
import com.example.cbuexchange.databinding.ItemExchangeBinding
import com.example.cbuexchange.entity.Currency
import com.example.cbuexchange.utils.getCurrentLocale
import java.util.Locale

class ExchangesAdapter(
    var context: Context, var list: List<Currency>, var listener: OnClickListener
) : RecyclerView.Adapter<ExchangesAdapter.Vh>() {/* var listExchanges: List<Currency>?, var listener: OnClickListener*/

    inner class Vh(var itemUserBinding: ItemExchangeBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun onBind(currency: Currency, position: Int) {
            itemUserBinding.apply {
                val currentLocale = getCurrentLocale(context)
                val language = currentLocale?.language
                when (language) {
                    "en" -> {
                        name.text = currency.ccy_name_en
                        date.text = "Updated ${currency.date}"
                    }

                    "ru" -> {
                        name.text = currency.ccy_name_ru
                        date.text = "Обновлено ${currency.date}"
                    }

                    "uz" -> {
                        name.text = currency.ccy_name_uz
                        date.text = "Yangilandi ${currency.date}"
                    }
                }
                name.isSelected = true
                price.text = "1 ${currency.ccy} = ${currency.rate} UZS"
                price.isSelected = true
                date.isSelected = true
                when (currency.status) {
                    0 -> {
                        val nightModeFlags =
                            context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
                        when (nightModeFlags) {
                            Configuration.UI_MODE_NIGHT_YES -> {
                                star.setImageResource(R.drawable.unselected_star_night)
                            }

                            Configuration.UI_MODE_NIGHT_NO -> {
                                star.setImageResource(R.drawable.unselected_star)

                            }

                            Configuration.UI_MODE_NIGHT_UNDEFINED -> {

                            }
                        }
                    }

                    1 -> {
                        star.setImageResource(R.drawable.selected_star)
                    }
                }
                star.setOnClickListener {
                    listener.onStarClickListener(star, position, currency)
                }
                currencyFlag.setImageResource(World.getFlagOf(currency.code))
            }
            itemUserBinding.root.setOnClickListener {
                listener.onClickListener(currency)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemExchangeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        list[position].let { holder.onBind(it, position) }
    }

    interface OnClickListener {
        fun onClickListener(currency: Currency)
        fun onStarClickListener(imageView: ImageView, position: Int, currency: Currency)
    }
}