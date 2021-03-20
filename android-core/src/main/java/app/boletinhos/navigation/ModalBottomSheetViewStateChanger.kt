package app.boletinhos.navigation

import android.app.Activity
import android.graphics.PixelFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import app.boletinhos.core.R
import app.boletinhos.navigation.ViewKey.ModalBottomSheet
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.StateChanger
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import app.boletinhos.core.R.id as Ids

class ModalBottomSheetViewStateChanger(
    activity: Activity,
    private val root: ViewGroup,
    private val windowManager: WindowManager
) : ViewStateChanger(activity, root), LifecycleObserver {
    private val StateChange.canUseDefaultChangeHandler: Boolean
        get() {
            if (isNavigatingFromViewToBottomSheet) {
                return false
            }

            val previousView = findPreviousViewOrNull(this)
            val shouldRestoreSheets = (previousKey == null || previousView == null)

            return !shouldRestoreSheets && newKey is ModalBottomSheet
        }

    override fun inflateViewForKey(
        key: ViewKey,
        stateChange: StateChange,
        createWithScope: Boolean
    ): View {
        if (key !is ModalBottomSheet) {
            return super.inflateViewForKey(key, stateChange, createWithScope)
        }

        val viewCoroutineName = CoroutineName("View [${key::class.java.name}]")
        val viewCoroutineScope = CoroutineScope(
            SupervisorJob() + Dispatchers.Main.immediate + viewCoroutineName
        )

        return wrapInBottomSheet(
            newView = super.inflateViewForKey(key, stateChange, false),
            newKey = key
        ).apply { setTag(Ids.view_coroutine_scope, viewCoroutineScope) }
    }

    override fun findPreviousViewOrNull(stateChange: StateChange): View? {
        val viewOnTopOfBottomSheet = stateChange.isGoingForward &&
                stateChange.isNavigatingFromBottomSheetToView

        if (stateChange.previousKey !is ModalBottomSheet || viewOnTopOfBottomSheet) {
            return super.findPreviousViewOrNull(stateChange)
        }

        return root.sheets.lastOrNull()
    }

    override fun onCancelViewScope(stateChange: StateChange, view: View) {
        // ViewScope must remain active when a bottom sheet is visible.
        if (stateChange.newKey !is ModalBottomSheet) {
            return super.onCancelViewScope(stateChange, view)
        }
    }

    override fun handleStateChange(
        stateChange: StateChange,
        completionCallback: StateChanger.Callback
    ) {
        if (stateChange.isGoingForward && stateChange.isNavigatingFromBottomSheetToView) {
            dismissAllSheets()
        }

        if (stateChange.canUseDefaultChangeHandler || stateChange.newKey !is ModalBottomSheet) {
            return super.handleStateChange(stateChange, completionCallback)
        }

        restoreBottomSheets(stateChange, completionCallback)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
        dismissAllSheets()
    }

    private fun wrapInBottomSheet(
        newKey: ModalBottomSheet,
        newView: View
    ): View {
        val modal = LayoutInflater.from(newView.context)
            .inflate(R.layout.modal_bottom_sheet, root, false)

        modal.findViewById<FrameLayout>(Ids.content)
            .addView(newView)

        modal.layoutParams = buildLayoutParams().apply {
            dimAmount = newKey.dimAmount
        }

        return modal
    }

    private fun buildLayoutParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
            WindowManager.LayoutParams.FLAG_DIM_BEHIND,
            PixelFormat.TRANSLUCENT
        ).apply { alpha = 1f }
    }

    private fun restoreBottomSheets(
        stateChange: StateChange,
        completionCallback: StateChanger.Callback
    ) {
        require(stateChange.newKey is ModalBottomSheet)

        val visibleSheetKey = stateChange.newKey
        val visibleSheetView = inflateViewForKey(visibleSheetKey, stateChange)

        val keys = stateChange.getNewKeys<ViewKey>() - visibleSheetKey
        val sheets = keys.filterIsInstance<ModalBottomSheet>()

        val visibleKeyInRoot = (keys - sheets).last()
        val visibleViewInRoot = inflateViewForKey(visibleKeyInRoot, stateChange)

        visibleViewInRoot.post {
            val views = sheets
                .mapNotNull { key ->
                    val isVisibleInHierarchy = keys.indexOf(key) > keys.indexOf(visibleKeyInRoot)
                    if (isVisibleInHierarchy) inflateViewForKey(key, stateChange) else null
                }

            views.forEach { view -> windowManager.addView(view, view.layoutParams) }

            visibleSheetKey.viewChangeHandler().performViewChange(
                root,
                visibleViewInRoot,
                visibleSheetView,
                StateChange.FORWARD
            ) {
                completionCallback.stateChangeComplete()
                root.setTag(Ids.modal_bottom_sheet_bag, views + visibleSheetView)
            }
        }

        val previousViewInRoot = root.getChildAt(0)

        if (previousViewInRoot != null) {
            onCancelViewScope(stateChange, previousViewInRoot)
            root.removeView(previousViewInRoot)
        }

        root.addView(visibleViewInRoot)
    }

    private fun dismissAllSheets() {
        root.sheets.forEach { view ->
            view.viewScope.cancel()
            windowManager.removeViewImmediate(view)
        }

        root.setTag(Ids.modal_bottom_sheet_bag, null)
    }
}
