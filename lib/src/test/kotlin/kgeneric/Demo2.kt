package kgeneric

class Demo2 {
    sealed class Nat {
        companion object {
            object Zero : Nat()
        }

        val Companion._0 get() = Zero
    }
}