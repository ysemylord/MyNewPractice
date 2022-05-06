package com.example.jetpackdemo.advance_ui.span_string

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.jetpackdemo.R


/**
 */
class SpanStringDemoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_span_string, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.text_view).run {
            val showedText = "大家好，大家好，大家好,大家好，大家好，大大家,大家好，大家好，大家好,大家好，大家好，大家好，大家好这是图片，你看好了"
            val ss = SpannableString(showedText)
               /*  ss.setSpan(
                     VerticalImageSpan(
                         context,
                         BitmapFactory.decodeResource(resources, R.drawable.fill_neutral)
                     ), showedText.indexOf("图片"), showedText.indexOf("图片")+2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                 )*/
            ss.setSpan(
                ForLineStraceImageSpan(
                    context,
                    R.drawable.fill_neutral,
                    DynamicDrawableSpan.ALIGN_BOTTOM
                ),
                showedText.indexOf("图片"),
                showedText.indexOf("图片") + 2,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            text = ss
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SpanStringDemoFragment()
    }
}