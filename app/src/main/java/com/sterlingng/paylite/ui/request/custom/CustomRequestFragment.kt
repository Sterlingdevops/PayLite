package com.sterlingng.paylite.ui.request.custom


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment

class CustomRequestFragment : BaseFragment() {

    override fun bindViews(view: View) {

    }

    override fun setUp(view: View) {

    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_custom_request, container, false)
    }
}
