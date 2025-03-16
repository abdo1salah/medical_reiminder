package com.example.medicalreiminder.model.utils

fun getEndpoint(barcode:String) = "/v3/products?barcode=${barcode}&formatted=y&key=v2l987spamjo6d0qp89k3wflszr1dn"
val BASE_URL = "https://api.barcodelookup.com"