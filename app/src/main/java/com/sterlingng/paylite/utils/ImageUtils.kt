package com.sterlingng.paylite.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.widget.ContentLoadingProgressBar
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.sterlingng.paylite.R
import java.lang.ref.WeakReference


object ImageUtils {

    /**
     * Crops image into a circle that fits within the ImageView.
     */
    fun displayRoundImageFromUrl(context: Context, url: String, imageView: ImageView) {
        try {
            val myOptions = RequestOptions()
                    .placeholder(R.drawable.champions_league32x32)
                    .fitCenter()
                    .centerCrop()
                    .dontAnimate()

            Glide.with(WeakReference(context).get()!!)
                    .asBitmap()
                    .apply(myOptions)
                    .load(url)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        } catch (e: NullPointerException) {

        } catch (e: IllegalArgumentException) {

        }

    }

    fun loadImageForFullScreen(context: Context, urlPhoto: String, progressBar: ContentLoadingProgressBar, imageView: ImageView) {
        try {
            val myOptions = RequestOptions()
                    .placeholder(R.drawable.champions_league32x32)
                    .override(640, 640)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .dontAnimate()

            Glide.with(WeakReference(context).get()!!)
                    .asBitmap()
                    .apply(myOptions)
                    .load(urlPhoto)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onLoadStarted(placeholder: Drawable?) {
                            progressBar.show()
                        }

                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            progressBar.hide()
                            imageView.setImageBitmap(resource)
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            Toast.makeText(context, "Image loading failed", Toast.LENGTH_LONG).show()
                            progressBar.hide()
                        }
                    })
        } catch (e: NullPointerException) {

        } catch (e: IllegalArgumentException) {

        }

    }

    fun displayImageFromUrlCustomPlaceHolder(context: Context, url: String, imageView: ImageView, resource: Int) {
        try {
            val myOptions = RequestOptions()
                    .placeholder(resource)
                    .fitCenter()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .dontAnimate()

            Glide.with(WeakReference(context).get()!!)
                    .asBitmap()
                    .apply(myOptions)
                    .load(url)
                    .into(imageView)
        } catch (e: NullPointerException) {

        } catch (e: IllegalArgumentException) {

        }

    }

    /**
     * Displays an image from a URL in an ImageView.
     */
    fun displayImageFromUrlWithListener(context: Context, url: String, imageView: ImageView, listener: RequestListener<Bitmap>) {
        try {
            val myOptions: RequestOptions = RequestOptions()
                    .placeholder(R.drawable.champions_league32x32)
                    .fitCenter()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()

            Glide.with(WeakReference(context).get()!!)
                    .asBitmap()
                    .apply(myOptions)
                    .load(url)
                    .listener(listener)
                    .into(imageView)
        } catch (e: NullPointerException) {

        } catch (e: IllegalArgumentException) {

        }

    }

    fun displayImageFromUrl(context: Context, url: String, imageView: ImageView) {
        try {
            val myOptions = RequestOptions()
                    .placeholder(R.drawable.champions_league32x32)
                    .fitCenter()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()

            Glide.with(WeakReference(context).get()!!)
                    .asBitmap()
                    .apply(myOptions)
                    .load(Uri.parse(url))
                    .into(imageView)
        } catch (e: NullPointerException) {

        } catch (e: IllegalArgumentException) {

        }
    }
}// Prevent instantiation
