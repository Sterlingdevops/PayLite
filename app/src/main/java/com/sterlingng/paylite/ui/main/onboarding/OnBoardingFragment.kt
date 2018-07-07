package com.sterlingng.paylite.ui.main.onboarding

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

data class ScreenData(val title: String, val info: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(info)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScreenData> {
        override fun createFromParcel(parcel: Parcel): ScreenData {
            return ScreenData(parcel)
        }

        override fun newArray(size: Int): Array<ScreenData?> {
            return arrayOfNulls(size)
        }
    }
}

class OnBoardingFragment : BaseFragment(), OnBoardingMvpView {
    @Inject
    lateinit var mPresenter: OnBoardingMvpContract<OnBoardingMvpView>

    private lateinit var backdrop: ImageView
    private lateinit var pay: TextView
    private lateinit var lite: TextView
    private lateinit var easy: TextView

    private lateinit var titleTextView: TextView
    private lateinit var infoTextView: TextView
    private lateinit var illustration: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        infoTextView = view.findViewById(R.id.info)
        titleTextView = view.findViewById(R.id.title)
        pay = view.findViewById(R.id.pay_text)
        lite = view.findViewById(R.id.lite_text)
        easy = view.findViewById(R.id.easy_text)
        backdrop = view.findViewById(R.id.backdrop)
        illustration = view.findViewById(R.id.illustration)
    }

    override fun setUp(view: View) {
        pay.visibility = View.GONE
        lite.visibility = View.GONE
        easy.visibility = View.GONE

        backdrop.setImageDrawable(ContextCompat.getDrawable(baseActivity, arguments?.getInt(resId)!!))
        if (arguments?.getBoolean(main)!!) {
            pay.visibility = View.VISIBLE
            lite.visibility = View.VISIBLE
            easy.visibility = View.VISIBLE

            infoTextView.visibility = View.GONE
            titleTextView.visibility = View.GONE
            illustration.visibility = View.GONE
        } else {
            pay.visibility = View.GONE
            lite.visibility = View.GONE
            easy.visibility = View.GONE

            infoTextView.visibility = View.VISIBLE
            titleTextView.visibility = View.VISIBLE
            illustration.visibility = View.VISIBLE
        }

        with(arguments?.getParcelable<ScreenData>(screenData)!!) {
            titleTextView.text = title
            infoTextView.text = info
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val resId = "backdrop"
        const val main = "main"
        const val screenData = "ScreenData"

        fun newInstance(res: Int, isMain: Boolean, data: ScreenData): Fragment {
            val fragment = OnBoardingFragment()
            val args = Bundle()
            args.putInt(resId, res)
            args.putBoolean(main, isMain)
            args.putParcelable(screenData, data)
            fragment.arguments = args
            return fragment
        }
    }
}
