package common

import kotlinx.coroutines.CoroutineScope

interface CoroutineScopeContainer : CoroutineScope {
    companion object {
        val TAG: String = CoroutineScopeContainer::class.java.name
    }
}
