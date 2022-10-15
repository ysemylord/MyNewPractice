package ch03

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine


suspend fun main() {
     suspend { 1 }.startCoroutine(object :Continuation<Int>{
         override val context: CoroutineContext
             get() = EmptyCoroutineContext

         override fun resumeWith(result: Result<Int>) {
              if(result.isSuccess){
                  result.getOrNull()
              }else{
                  result.exceptionOrNull()
              }
         }

     })
}

class Ch032SuspendMain {
}