package com.sterlingng.paylite.data.model

data class VAService(var index: Int, var name: String, var resId: Int, var providers: ArrayList<VasProvider>)
data class VasProvider(var index: Int, var name: String, var resId: Int)