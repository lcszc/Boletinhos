package app.boletinhos.navigation

import android.os.Parcelable
import com.zhuinden.simplestack.navigator.DefaultViewKey
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestack.navigator.changehandlers.FadeViewChangeHandler

@SuppressWarnings("MagicNumber")
interface ViewKey : DefaultViewKey, Parcelable {
    override fun viewChangeHandler(): ViewChangeHandler {
        return FadeViewChangeHandler()
    }

    interface ModalBottomSheet : ViewKey {
        val dimAmount: Float get() = 0.8f

        override fun viewChangeHandler(): ViewChangeHandler {
            return ModalBottomSheetChangeHandler()
        }
    }
}
