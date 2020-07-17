package app.boletinhos.navigation

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import app.boletinhos.core.R.id as Ids
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.StateChanger
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestack.navigator.changehandlers.FadeViewChangeHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class ViewStateChanger(
    private val activity: Activity,
    private val root: ViewGroup
) : StateChanger {
    private val immediateDispatcher: CoroutineDispatcher by lazy {
        Dispatchers.Main.immediate
    }

    override fun handleStateChange(stateChange: StateChange, completionCallback: StateChanger.Callback) {
        val newKey = stateChange.topNewKey<ViewKey>()
        val previousKey = stateChange.topPreviousKey<ViewKey?>()

        val viewCoroutineName = CoroutineName("View [${newKey::class.java.name}]")
        val viewCoroutineScope = CoroutineScope(
            SupervisorJob() + immediateDispatcher + viewCoroutineName
        )

        val newView = LayoutInflater.from(stateChange.createContext(activity, newKey))
            .inflate(newKey.layout(), root, false)

        newView.setTag(Ids.view_coroutine_scope, viewCoroutineScope)

        val previousView = root.getChildAt(0)
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

        (previousView.getTag(Ids.view_coroutine_scope) as CoroutineScope?)?.cancel()

        viewChangeHandler.performViewChange(root, previousView, newView, stateChange.direction) {
            completionCallback.stateChangeComplete()
        }
    }
}
