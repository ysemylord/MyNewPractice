package 课程.listen07

/**
 * 密闭类是一个抽象类，且子类只能定义在密闭类所在的文件
 * 密闭类，子类可数
 */
sealed class PlayerState
object Idle : PlayerState()
class Playing(val song: String) : PlayerState() {
    fun play() {}
    fun stop() {}
}

class Error(val errorInt: String) : PlayerState() {
    fun recover() {}
}

class Player {
    var state: PlayerState = Idle
    fun play() {
        state = when (val state = state) {
            Idle -> {
                Playing("song1").also {
                    it.play()
                }
            }
            is Playing -> {
                state.stop()
                Playing("song2").also {
                    it.play()
                }
            }
            is Error -> {
                state.recover()
                Playing("song2").also {
                    it.play()
                }
            }
        }
    }


}