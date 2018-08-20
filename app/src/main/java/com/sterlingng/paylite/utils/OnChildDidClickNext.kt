package com.sterlingng.paylite.utils

interface OnChildDidClickNext {
    fun onPreviousClick(index: Int)
    fun onNextClick(index: Int, data: Any)
}