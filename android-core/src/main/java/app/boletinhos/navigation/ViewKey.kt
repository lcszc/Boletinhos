package app.boletinhos.navigation

import android.os.Parcelable
import com.zhuinden.simplestack.navigator.DefaultViewKey
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestack.navigator.changehandlers.FadeViewChangeHandler

interface ViewKey : DefaultViewKey, Parcelable {
    override fun viewChangeHandler(): ViewChangeHandler {
        return FadeViewChangeHandler()
    }
}
