package app.boletinhos.navigation

import app.boletinhos.navigation.ViewKey.ModalBottomSheet
import com.zhuinden.simplestack.StateChange

internal val StateChange.newKey get() = topNewKey<ViewKey>()

internal val StateChange.previousKey get() = topPreviousKey<ViewKey?>()

internal val StateChange.isGoingBackward get() = direction == StateChange.BACKWARD

internal val StateChange.isGoingForward get() = direction in StateChange.REPLACE..StateChange.FORWARD

internal val StateChange.isNavigatingFromBottomSheetToView
    get() = newKey !is ModalBottomSheet && previousKey is ModalBottomSheet

internal val StateChange.isNavigatingFromViewToBottomSheet
    get() = isGoingBackward && (previousKey !is ModalBottomSheet && newKey is ModalBottomSheet)

