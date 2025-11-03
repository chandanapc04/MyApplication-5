package com.example.myapplication.billing

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class BillingViewModel(application: Application) : AndroidViewModel(application) {

    private val billingClientWrapper = BillingClientWrapper(application)

    val productDetails = billingClientWrapper.productDetails.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    val purchases = billingClientWrapper.purchases.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    init {
        billingClientWrapper.startConnection()
    }

    fun launchPurchaseFlow(activity: Activity, productDetails: ProductDetails) {
        billingClientWrapper.launchPurchaseFlow(activity, productDetails)
    }
}