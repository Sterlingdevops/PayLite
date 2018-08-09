package com.sterlingng.paylite.ui.give.projects


import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Project
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.ui.give.OnFilterClicked
import com.sterlingng.paylite.ui.project.ProjectActivity
import com.sterlingng.paylite.utils.ItemOffsetDecoration
import javax.inject.Inject

class ProjectsFragment : BaseFragment(), ProjectsMvpView,
        ProjectsAdapter.OnRetryClicked,
        OnFilterClicked,
        FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: ProjectsMvpContract<ProjectsMvpView>

    @Inject
    lateinit var mGridLayoutManager: GridLayoutManager

    @Inject
    lateinit var mProjectsAdapter: ProjectsAdapter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var itemOffsetDecoration: ItemOffsetDecoration

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_projects, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        itemOffsetDecoration = ItemOffsetDecoration(baseActivity, R.dimen.item_offset)
        mProjectsAdapter.mRecyclerViewClickListener = this
        mProjectsAdapter.onRetryClickedListener = this
        mRecyclerView.adapter = mProjectsAdapter
        mRecyclerView.layoutManager = mGridLayoutManager
        mRecyclerView.addItemDecoration(itemOffsetDecoration)
        mRecyclerView.scrollToPosition(0)

        mPresenter.loadProjects()
    }

    override fun onRetryClicked() {

    }

    override fun onFilterClicked() {
        val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
        filterBottomSheetFragment.onFilterItemSelectedListener = this
        filterBottomSheetFragment.title = "Project"
        filterBottomSheetFragment.items = listOf("Health", "Education", "Agriculture", "Transportation")
        filterBottomSheetFragment.show(baseActivity.supportFragmentManager, "filter")
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        show(s, true)
        dialog.dismiss()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {
        val intent = ProjectActivity.getStartIntent(baseActivity)
        startActivity(intent)
    }

    override fun updateProjects(it: ArrayList<Project>) {
        mProjectsAdapter.addCategories(it)
    }

    companion object {

        fun newInstance(): ProjectsFragment {
            val fragment = ProjectsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
