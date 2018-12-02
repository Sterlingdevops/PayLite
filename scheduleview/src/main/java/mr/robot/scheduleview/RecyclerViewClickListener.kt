package mr.robot.scheduleview

import android.view.View

interface RecyclerViewClickListener {
    fun recyclerViewItemClicked(v: View, position: Int)
}