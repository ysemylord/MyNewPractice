package 课程.listen06

/**
 * 涉及到的只是点
 *
 * + 对Receiver的理解
 *   Receiver本质上是一个参数
 *   但是在使用时，它要理解为函数的接受者，函数的调用者，函数的作用范围
 *   这样在就能写出更加灵活的程序
 *
 * + 操作符重载
 *  ()  -> invoke
 *  +A  -> unary
 * + 集合的变化
 *  map
 *  joinToString
 *
 */
class HtmlDSL {
}

interface Node {
    fun render(): String
}

class StringNode(val content: String) : Node {
    override fun render(): String {
        return content
    }

}

class BlockNode(val name: String) : Node {

    val children = mutableListOf<BlockNode>()
    val properties = mutableMapOf<String, Any?>()

    //<html key='value' key='value'></html>
    //children.joinToString { it.render() }这句话不好理解就不理解了，哈哈哈
    override fun render(): String {
        return "<$name ${
            properties.map { "${it.key} = '${it.value}'" }.joinToString { " " }
        } > ${children.joinToString { it.render() }} </$name>"
    }


    //String的invoke操作符重载
    operator fun String.invoke(block: BlockNode.() -> Unit): BlockNode {
        val blockNode = BlockNode(this)
        blockNode.block()
        children + this
        return blockNode
    }

    operator fun String.invoke(value: Any?) {
        properties[this] = value
    }

    operator fun String.unaryPlus(): StringNode {
        val stringNode = StringNode(this)
        children + this
        return stringNode
    }

}


fun html(block: BlockNode.() -> Unit): BlockNode {
    val html = BlockNode("html")
    html.block()
    return html
}

fun BlockNode.head(block: BlockNode.() -> Unit): BlockNode {
    val head = BlockNode("head")
    head.block()
    this.children + head//this 指的是head的Receiver
    return head
}

fun BlockNode.body(block: BlockNode.() -> Unit): BlockNode {
    val body = BlockNode("body")
    body.block()
    this.children + body//this 指的是head的Receiver
    return body
}



