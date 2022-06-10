package coroutine_demo.classes.ch05

import com.kotlin_coroutines.lanch
import coroutine_demo.classes.ch05.core.StandaloneCoroutine
import coroutine_demo.classes.ch05.scope.GlobalScope
import coroutine_demo.classes.ch05.scope.ScopeCoroutine
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

class 作用域 {
}

fun main() {
    GlobalScope.launch {// 根协程，顶级作用域

        launch {//子协程，子协程的作用域：协同作用域

        }
    }
}

/**
 * 在suspend函数中怎么开始子协程
 * 使用suspendCoroutine函数获取continuation,
 * 这个continuaction就是一个协程上下文
 */
private suspend fun noScope() {
    suspendCoroutine<Unit> { continuation ->
         val coroutineScope = ScopeCoroutine(continuation.context,continuation)
        coroutineScope.launch {  }
    }
}