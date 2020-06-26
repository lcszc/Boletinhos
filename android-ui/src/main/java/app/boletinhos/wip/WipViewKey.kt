package app.boletinhos.wip

import com.zhuinden.simplestack.navigator.DefaultViewKey
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestack.navigator.changehandlers.FadeViewChangeHandler
import app.boletinhos.R.layout as Layouts

object WipViewKey : DefaultViewKey {
    override fun layout(): Int {
        return Layouts.wip_view
    }

    override fun viewChangeHandler(): ViewChangeHandler {
        return FadeViewChangeHandler()
    }
}
