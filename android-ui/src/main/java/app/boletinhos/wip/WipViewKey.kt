package app.boletinhos.wip

import android.os.Parcelable
import com.zhuinden.simplestack.navigator.DefaultViewKey
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestack.navigator.changehandlers.FadeViewChangeHandler
import kotlinx.android.parcel.Parcelize
import app.boletinhos.R.layout as Layouts

@Parcelize
data class WipViewKey(val ab: String = "") : DefaultViewKey, Parcelable {
    override fun layout(): Int {
        return Layouts.wip_view
    }

    override fun viewChangeHandler(): ViewChangeHandler {
        return FadeViewChangeHandler()
    }
}
