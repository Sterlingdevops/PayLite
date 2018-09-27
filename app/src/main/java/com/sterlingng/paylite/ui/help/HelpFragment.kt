package com.sterlingng.paylite.ui.help

import android.annotation.TargetApi
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject


class HelpFragment : BaseFragment(), HelpMvpView {

    @Inject
    lateinit var mPresenter: HelpMvpContract<HelpMvpView>

    private lateinit var mWebView: WebView
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        exit.visibility = View.GONE
        mWebView.settings.javaScriptEnabled = true // enable javascript
        mWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                show(description, true)
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }
        }

        mWebView.loadUrl("http://stellabot.sterlingapps.p.azurewebsites.net/stella.htm?mobile=" +
                "${mPresenter.getUserPhone()}&name=${mPresenter.getUserName()}")
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mWebView = view.findViewById(R.id.webView)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): HelpFragment {
            val fragment = HelpFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
