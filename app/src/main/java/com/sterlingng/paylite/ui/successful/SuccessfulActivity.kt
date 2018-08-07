package com.sterlingng.paylite.ui.successful

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.utils.ItemOffsetDecoration
import com.sterlingng.paylite.utils.widgets.NoScrollingGridLayoutManager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class SuccessfulActivity : BaseActivity(), SuccessfulMvpView, CategoriesAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: SuccessfulMvpContract<SuccessfulMvpView>

    @Inject
    lateinit var mGridLayoutManager: NoScrollingGridLayoutManager

    @Inject
    lateinit var mCategoriesAdapter: CategoriesAdapter

    private lateinit var next: Button
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var itemOffsetDecoration: ItemOffsetDecoration

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        next.visibility = View.GONE

        itemOffsetDecoration = ItemOffsetDecoration(this, R.dimen.item_offset)
        mCategoriesAdapter.mRecyclerViewClickListener = this
        mCategoriesAdapter.onRetryClickedListener = this

        mGridLayoutManager.spanCount = 3
        mGridLayoutManager.isScrollEnabled = false

        mRecyclerView.adapter = mCategoriesAdapter
        mRecyclerView.layoutManager = mGridLayoutManager
        mRecyclerView.addItemDecoration(itemOffsetDecoration)
        mRecyclerView.scrollToPosition(0)

        mPresenter.loadCategories()
    }

    override fun updateCategories(categories: ArrayList<PaymentCategory>) {
        mCategoriesAdapter.addCategories(categories)
    }

    override fun onRetryClicked() {

    }

    override fun bindViews() {
        next = findViewById(R.id.next)
        mRecyclerView = findViewById(R.id.recyclerView)
    }

    var mSelectedCategory = -1
    override fun recyclerViewListClicked(v: View, position: Int) {
        mSelectedCategory = position
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, SuccessfulActivity::class.java)
        }
    }
}
