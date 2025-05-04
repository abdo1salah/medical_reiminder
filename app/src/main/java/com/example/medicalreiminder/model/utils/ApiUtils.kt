package com.example.medicalreiminder.model.utils

fun getEndpoint(barcode:String) = "/v3/products?barcode=${barcode}&formatted=y&key=n8f9e21rfrn43muthl0h03vu9mq3t9"
val BASE_URL = "https://api.barcodelookup.com"