package com.example.rentacar.viewmodel

import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val affiliateId = "awesomecars"
    private val kayakDomain = "www.kayak.com"

    fun generateKayakUrl(pickup: String, dropOff: String, pickupDate: String, dropOffDate: String): String {
        val dropOffPath = if (dropOff.isNotEmpty()) dropOff else ""
        return "https://$kayakDomain/in?a=$affiliateId&url=/cars/$pickup/$dropOffPath/$pickupDate/$dropOffDate"
    }
}
