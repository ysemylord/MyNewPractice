package coroutine_demo.classes.ch05

import com.kotlin_coroutines.lanch
import com.kotlin_demo.coroutines_demo.scope_demo.log
import coroutine_demo.classes.ch05.core.StandaloneCoroutine
import coroutine_demo.classes.ch05.exception.CoroutineExceptionHandler
import coroutine_demo.classes.ch05.scope.GlobalScope
import coroutine_demo.classes.ch05.scope.ScopeCoroutine
import coroutine_demo.classes.ch05.scope.coroutineScope
import coroutine_demo.classes.ch05.scope.supervisorScope
import org.junit.Test


/**
 * 一个协程发生异常
 * 如果其所在的作用域为协同作用域，则其所在的协同作用域的协程取消并向上抛这个异常
 * 如果其所在的作用域为主从作用域，则其所在的主从作用域的协程不处理这个异常，让发生异常的协程自己处理。
 */
class 作用域 {

    @Test
    fun testScope() {
        GlobalScope.launch {//使用顶级作用域创建协程， 创建的协程没有父协程，称为根协程，
            log(scopeContext::class.java.name)
        }
        Thread.sleep(3000)
    }

    @Test
    fun 顶级作用域() {
        GlobalScope.launch { //1
            GlobalScope.launch {//2

            }
            launch { //3

            }
        }
    }


}


/**
 * 在suspend函数中怎么开启子协程
 */
private suspend fun noScope() {
    coroutineScope { //子协程，协同作用域
        log("3")
    }
}