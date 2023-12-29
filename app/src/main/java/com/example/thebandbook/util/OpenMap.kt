package com.example.thebandbook.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast


fun openMap(address: String, context: Context) {
    // Try to open in Google Maps app
    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")

    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
        setPackage("com.google.android.apps.maps")
    }

    try {
        context.startActivity(mapIntent)
    }
    catch (e: Exception) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}"))
            context.startActivity(browserIntent)
        }
        catch (e: Exception) {
            Toast.makeText(context, "No application found to open the map!", Toast.LENGTH_SHORT)
                .show()
        }
    }
}

