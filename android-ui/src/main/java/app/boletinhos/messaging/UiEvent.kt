package app.boletinhos.messaging

import androidx.annotation.StringRes

sealed class UiEvent {
    data class ResourceMessage(@StringRes val messageRes: Int) : UiEvent()
}
