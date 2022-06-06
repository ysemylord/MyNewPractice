package common

object Dispatchers {
    val Android by lazy {
        DispatcherContext(HandlerDispatcher)
    }
    val Default by lazy {
        DispatcherContext(DefaultDispatcher)
    }

    val Single by lazy {
        DispatcherContext(SingleDispatcher)
    }
}