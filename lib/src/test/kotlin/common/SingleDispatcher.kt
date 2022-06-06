package common

import java.util.concurrent.Executors

object SingleDispatcher : Dispatcher {

    private val single = Executors.newSingleThreadExecutor { runable ->
        Thread(runable).apply {
            name = "全局唯一Dispatcher线程"
        }
    }

    override fun dispatch(block: () -> Unit) {
        single.submit {
            block()
        }
    }
}