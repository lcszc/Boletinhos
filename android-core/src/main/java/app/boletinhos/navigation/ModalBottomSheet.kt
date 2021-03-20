package app.boletinhos.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import app.boletinhos.core.R
import app.boletinhos.core.databinding.ModalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestackextensions.navigatorktx.backstack

@Suppress("UNCHECKED_CAST")
internal val ViewGroup.sheets: List<View> get() {
    return (getTag(R.id.modal_bottom_sheet_bag) as List<View>?).orEmpty()
}

internal class ModalBottomSheetCallback : BottomSheetBehavior.BottomSheetCallback() {
    var onNewState: ((newState: Int) -> Unit)? = null
    var onSlide: ((offset: Float) -> Unit)? = null

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        onNewState?.invoke(newState)
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        onSlide?.invoke(slideOffset)
    }
}

internal class ModalBottomSheetLayout(
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout(context, attrs) {
    private val binding by lazy {
        ModalBottomSheetBinding.bind(this)
    }

    private val dismissCallback = ModalBottomSheetCallback()

    private val behavior by lazy {
        BottomSheetBehavior.from(binding.content)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        behavior.addBottomSheetCallback(dismissCallback)

        dismissCallback.onSlide = { offset ->
            if (offset == -1f) {
                behavior.removeBottomSheetCallback(dismissCallback)
                tryToDismiss()
            }
        }

        binding.viewTouchOutside.setOnClickListener {
            tryToDismiss()
        }
    }

    override fun onDetachedFromWindow() {
        behavior.removeBottomSheetCallback(dismissCallback)
        super.onDetachedFromWindow()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val isBackButtonPressed = event?.keyCode == KEYCODE_BACK && event.action == ACTION_DOWN
        if (isBackButtonPressed) return tryToDismiss()
        return super.dispatchKeyEvent(event)
    }

    private fun tryToDismiss(): Boolean {
        if (behavior.isHideable) return backstack.goBack()
        return true
    }
}

open class ModalBottomSheetChangeHandler : ViewChangeHandler {
    override fun performViewChange(
        container: ViewGroup,
        previousView: View,
        newView: View,
        direction: Int,
        viewChangeCallback: ViewChangeHandler.ViewChangeCallback
    ) {
        val windowManager = container.context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val isNavigatingBack = direction == StateChange.BACKWARD

        val bottomSheet = (if(isNavigatingBack) previousView else newView).findBottomSheet()
        val behavior = BottomSheetBehavior.from(bottomSheet)

        if (isNavigatingBack) {
            if (behavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                windowManager.removeViewImmediate(previousView)
                container.setTag(R.id.modal_bottom_sheet_bag, container.sheets - previousView)
                viewChangeCallback.onCompleted()
                return
            }

            val dismissCallback = ModalBottomSheetCallback()
            dismissCallback.onNewState = { newState ->
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.removeBottomSheetCallback(dismissCallback)

                    windowManager.removeViewImmediate(previousView)
                    container.setTag(R.id.modal_bottom_sheet_bag, container.sheets - previousView)
                    viewChangeCallback.onCompleted()
                }
            }

            behavior.addBottomSheetCallback(dismissCallback)
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
            return
        }

        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        windowManager.addView(newView, newView.layoutParams)

        bottomSheet.post {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            container.setTag(R.id.modal_bottom_sheet_bag, container.sheets + newView)
            viewChangeCallback.onCompleted()
        }
    }

    private fun View.findBottomSheet() = findViewById<FrameLayout>(R.id.content)
}
