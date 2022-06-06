package common

import android.os.Handler
import android.os.Looper

object HandlerDispatcher : Dispatcher {
    override fun dispatch(block: () -> Unit) {
        Handler(Looper.getMainLooper()).post {
            block
        }
    }
}