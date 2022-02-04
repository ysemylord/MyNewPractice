package com.example.jetpackdemo.advance_ui.draw_text_gradient

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jetpackdemo.R
import java.time.Duration


class DrawFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_draw, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textGradientCenter = view.findViewById<DrawTextGradientCenter>(R.id.text_view)
        ObjectAnimator.ofFloat(textGradientCenter, "percent",0f,1f)
            .run {
                duration = (3000L)
                repeatCount = -1
                start()

            }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DrawFragment()
    }
}