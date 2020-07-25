package app.boletinhos.testutil

import android.os.Parcelable
import com.zhuinden.simplestack.navigator.DefaultViewKey
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestack.navigator.changehandlers.NoOpViewChangeHandler
import kotlinx.android.parcel.Parcelize

@Parcelize
open class TestKey : DefaultViewKey, Parcelable {
    override fun layout(): Int = 0

    override fun viewChangeHandler(): ViewChangeHandler {
        return NoOpViewChangeHandler()
    }

    override fun describeContents(): Int = 0
}
