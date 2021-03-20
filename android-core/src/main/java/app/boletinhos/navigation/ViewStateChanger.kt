package app.boletinhos.navigation

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.StateChanger
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestack.navigator.changehandlers.FadeViewChangeHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import app.boletinhos.core.R.id as Ids

open class ViewStateChanger(
    private val activity: Activity,
    private val root: ViewGroup
) : StateChanger, LifecycleObserver {
    internal open fun inflateViewForKey(
        key: ViewKey,
        stateChange: StateChange,
        createWithScope: Boolean = true
    ): View {
        val newView = LayoutInflater.from(stateChange.createContext(activity, key))
            .inflate(key.layout(), root, false)

        if (!createWithScope) return  newView

        val viewCoroutineName = CoroutineName("View [${key::class.java.name}]")
        val viewCoroutineScope = CoroutineScope(
            SupervisorJob() + Dispatchers.Main.immediate + viewCoroutineName
        )

        newView.setTag(Ids.view_coroutine_scope, viewCoroutineScope)
        return newView
    }

    internal open fun findPreviousViewOrNull(stateChange: StateChange): View? {
        return root.getChildAt(0)
    }

    internal open fun onCancelViewScope(stateChange: StateChange, view: View) {
        (view.getTag(Ids.view_coroutine_scope) as CoroutineScope?)?.cancel()
    }

    override fun handleStateChange(stateChange: StateChange, completionCallback: StateChanger.Callback) {
        val newKey = stateChange.topNewKey<ViewKey>()
        val previousKey = stateChange.topPreviousKey<ViewKey?>()

        val newView = inflateViewForKey(newKey, stateChange)
        val previousView = findPreviousViewOrNull(stateChange)

        Navigator.persistViewToState(previousView)
        Navigator.restoreViewFromState(newView)

        if (previousKey == null || previousView == null) {
            root.addView(newView)
            completionCallback.stateChangeComplete()
            return
        }

        val viewChangeHandler = when (stateChange.direction) {
            StateChange.FORWARD -> newKey.viewChangeHandler()
            StateChange.BACKWARD -> previousKey.viewChangeHandler()
            else -> FadeViewChangeHandler()
        }

        onCancelViewScope(stateChange, previousView)

        viewChangeHandler.performViewChange(root, previousView, newView, stateChange.direction) {
            completionCallback.stateChangeComplete()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        root.children
            .filter { view -> view.getTag(Ids.view_coroutine_scope) != null }
            .forEach { view -> view.viewScope.cancel() }
    }
}
