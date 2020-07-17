package app.boletinhos.navigation

import android.view.View
import android.view.ViewGroup
import app.boletinhos.core.R.id as Ids
import kotlinx.coroutines.CoroutineScope

private tailrec fun View.findTopMostParentWithCoroutineScope(): CoroutineScope {
    val scope = getTag(Ids.view_coroutine_scope) as CoroutineScope?
    return scope ?: checkNotNull(parent as ViewGroup) {
        "ViewCoroutineScope wasn't been set for this view tree hierarchy."
    }.findTopMostParentWithCoroutineScope()
}

val View.viewScope: CoroutineScope get() = findTopMostParentWithCoroutineScope()
