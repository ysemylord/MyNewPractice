package com.example.customview.MyLayoutInflaterFactory.utils

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * 所有View
 */
class SkinAttribute(private val skinViews: MutableList<SkinView> = mutableListOf()) {
    companion object {
        //要被替换的属性
        val mAttributesNeedApply = listOf(
            "background",
            "src",
            "textColor",
            /*    "drawableLeft",
                "drawableStart",
                "drawableRight",
                "drawableEnd",
                "drawableTop",
                "drawableBottom"*/
        )
    }

    /**
     * 对所有View进行属性修改
     */
    fun applySkin() {
        skinViews.forEach {
            it.applySkin()
        }
    }

    /**
     * 记录view上需要替换的属性
     * @param view
     * @param attributeSet 从xml中读取的View的所有属性
     */
    fun look(view: View, attributeSet: AttributeSet) {
        val skinPairs = mutableListOf<SkinPair>()
        var index = 0
        while (index < attributeSet.attributeCount) {
            val attributeName = attributeSet.getAttributeName(index)
            if (mAttributesNeedApply.contains(attributeName)) {//如果是需要修改的属性
                val resValue = attributeSet.getAttributeValue(index)
                //字符串表示的资源
                //#ff000000
                //?ff000000
                //@2131034145

                if (resValue.startsWith("#")) {//以#开头，表示写死的颜色，不处理
                    return
                } else if (resValue.startsWith("@")) {
                    skinPairs.add(
                        SkinPair(
                            attributeName,
                            resValue.substring(1).toInt()
                        )
                    )
                }
            }
            index++
        }
        skinViews.add(SkinView(view, skinPairs))
    }


}

/**
 * View和它对需要替换的资源
 */
class SkinView(private val view: View, val skinPairs: List<SkinPair>) {

    /**
     * 对一个view的属性进行修改
     */
    fun applySkin() {
        skinPairs.forEach {
            when (it.name) {
                "textColor" -> {
                    val colorId = SkinResources.getInstance().getColor(it.resId)
                    (view as? TextView)?.setTextColor(colorId)
                }
                "background" -> {
                    val drawableRes = SkinResources.getInstance().getBackground(it.resId)
                    view.run {
                        if (drawableRes is Drawable) {
                            background = drawableRes
                        } else {
                            setBackgroundColor(drawableRes as Int)
                        }
                    }
                }

                "src" -> {
                    val drawableRes = SkinResources.getInstance().getSrc(it.resId)
                    (view as? ImageView)?.run {
                        if (drawableRes is Drawable) {
                            setImageDrawable(drawable)
                        } else {
                            setImageDrawable(ColorDrawable(drawableRes as Int))
                        }
                    }
                }
            }
        }
    }

}

/**
 * 资源名和对应的资源Id
 * myBlack R.color.myBlack
 */
data class SkinPair(val name: String, val resId: Int)