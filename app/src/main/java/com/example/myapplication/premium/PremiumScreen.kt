package com.example.myapplication.premium

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.billing.BillingViewModel

@Composable
fun PremiumScreen(billingViewModel: BillingViewModel = viewModel()) {
    val productDetails by billingViewModel.productDetails.collectAsState()
    val purchases by billingViewModel.purchases.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (purchases.any { it.products.contains("android.test.purchased") }) {
            Text("You are a premium user!")
        } else {
            productDetails.forEach { product ->
                Button(
                    onClick = {
                        (context as? Activity)?.let { activity ->
                            billingViewModel.launchPurchaseFlow(activity, product)
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Buy ${product.name}")
                }
            }
        }
    }
}
