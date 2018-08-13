package com.sterlingng.views

interface DrawableClickListener {
    fun onClick(target: DrawablePosition)
    enum class DrawablePosition {
        TOP, BOTTOM, LEFT, RIGHT
    }
}