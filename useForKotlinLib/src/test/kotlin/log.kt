fun String.log() {
    println(
        "useful info ${
            Thread.currentThread().run {
                name + " " + hashCode()
            }
        } $this"
    )
}
