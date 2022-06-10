package coroutine_demo.classes.ch05.cancel

import coroutine_demo.classes.ch05.OnCancel


sealed class CancelState {
    override fun toString(): String {
        return "CancelState.${this.javaClass.simpleName}"
    }
    object InComplete : CancelState()
    class CancelHandler(val onCancel: OnCancel): CancelState()//CancelHandler和InComplete视为同意状态，只是多了挂起点的取消回调
    class Complete<T>(val value: T? = null, val exception: Throwable? = null) : CancelState()
    object Cancelled : CancelState()
}