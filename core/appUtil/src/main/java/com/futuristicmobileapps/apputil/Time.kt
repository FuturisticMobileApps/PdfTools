package com.futuristicmobileapps.apputil

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


val MMM_DD_YYYY__HH_MM_a = SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.ENGLISH)

fun Long.toDate(dateFormat : SimpleDateFormat = MMM_DD_YYYY__HH_MM_a): String = dateFormat.format(Date(this))