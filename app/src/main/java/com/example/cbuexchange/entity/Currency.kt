package com.example.cbuexchange.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

class Currency(
    @PrimaryKey
    val currency_id: Int,
    val ccy: String,
    val ccy_name_en: String,
    val ccy_name_ru: String,
    val ccy_name_uz: String,
    val code: String,
    val date: String,
    val rate: String,
    var status: Int
)