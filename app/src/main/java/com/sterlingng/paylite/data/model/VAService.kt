package com.sterlingng.paylite.data.model

data class VAService(var name: String, var resId: Int, var providers: ArrayList<VasProvider>)
data class VasProvider(var name: String, var resId: Int)