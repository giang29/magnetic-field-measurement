package la.me.leo.magneticmeasurement

import android.view.View
import android.view.ViewGroup

inline fun ViewGroup.forEach(action: (view: View) -> Unit) {
    for (index in 0 until childCount) {
        action(getChildAt(index))
    }
}

fun ViewGroup.turnViewsToVisible(visibleViewIds: Set<Int>) {
    forEach {
        if (visibleViewIds.contains(it.id)) {
            it.visibility == View.VISIBLE
        } else {
            it.visibility == View.GONE
        }
    }
}
