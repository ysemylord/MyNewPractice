package com.example.otherapplication

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


/**
 * DialogFragment
 * 1.window param 默认为 wrap_content wrap_content
 * 2.window gravity 默认为 center
 * 3. window 默认是圆角，白色背景，与padding不为0，使用style解决
 * 4. DialogFragment没有显示出来，getDialog()==null
 */
class MyBaseDialogFragment : DialogFragment() {

    private val spannableString by lazy {
        val str =
            "Wow you’re a Top Fan! Your username is visible to everyone, but you can " +
                    "change your settings here. "
        val spannableString = SpannableString(str)
        val spannableEnd = spannableString.length
        val spannableStart = spannableEnd-"change your settings here.".length
       // spannableString.setSpan(ForegroundColorSpan(Color.BLUE),spannableStart,spannableEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(object:ClickableSpan(){
            override fun onClick(widget: View) {
               Log.i("MyBaseDialogFragment","click")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.BLUE
                ds.isUnderlineText=false

            }
        },spannableStart,spannableEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString
    }

    init {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.nine_png_test, container, false)
        val textView =inflate.findViewById<TextView>(R.id.nine_text_view)
        textView?.run {
            //textView.text="textView"
            textView.setBackgroundResource(R.drawable.tool_tip_t)
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT//点击后的背景色
            //设置点击后的颜色为透明（有默认背景）
            textView.text = spannableString

        }

        return inflate
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (dialog.window == null) {
            return dialog
        }
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.gravity = Gravity.START or Gravity.TOP
        dialog.window!!.attributes = params
        dialog.window!!.setDimAmount(0f)
        return dialog
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().remove(this).commitAllowingStateLoss()//avoid add more than once
        super.show(manager, tag)
    }

    var x = 0
    var y = 0
    fun showPosition(x: Int, y: Int, manager: FragmentManager, tag: String?) {
        this.x = x
        this.y = y
        show(manager, tag)
    }

    override fun onResume() {
        if (dialog == null || dialog?.window == null) {
            return
        }
        val params: WindowManager.LayoutParams = dialog!!.window!!.attributes
        params.x = x
        params.y = y
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params
        super.onResume()
    }
}