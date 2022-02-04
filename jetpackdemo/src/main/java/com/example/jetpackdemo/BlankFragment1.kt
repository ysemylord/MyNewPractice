package com.example.jetpackdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BlankFragment1 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_blank, container, false)
        inflate?.findViewById<TextView>(R.id.init_textView)?.text = "初始参数 $param1|$param2"
        inflate?.findViewById<View>(R.id.btn)?.setOnClickListener {
            it.findNavController().navigate(R.id.action_global_globaleActionFragment)
        }
        inflate?.findViewById<View>(R.id.btn_to_fragment2)?.setOnClickListener {
            it.findNavController().navigate(R.id.action_blankFragment1_to_blankFragment2)
        }
        inflate?.findViewById<View>(R.id.test_safe_args)?.setOnClickListener {

            // it.findNavController().
            // navigate(R.id.action_blankFragment1_to_safeArgsFragment, bundleOf("param1" to "传递的参数"))

            //使用Safe Args生成的NavDirections跳转
            val action = BlankFragment1Directions.actionBlankFragment1ToSafeArgsFragment("传递的参数")
            it.findNavController().navigate(action)
        }
        inflate?.findViewById<View>(R.id.to_room)?.setOnClickListener {
            val action = BlankFragment1Directions.actionBlankFragment1ToRoomDemoFragment()
            it.findNavController().navigate(action)
        }

        inflate?.findViewById<View>(R.id.to_dispatch_touch_event_fragment)?.setOnClickListener {
            val action = BlankFragment1Directions.actionBlankFragment1ToDispatchTouchEventFragment()
            it.findNavController().navigate(action)
        }

        inflate?.findViewById<View>(R.id.to_draw_fragment)?.setOnClickListener {
            val action = BlankFragment1Directions.actionBlankFragment1ToDrawFragment()
            it.findNavController().navigate(action)
        }

        inflate?.findViewById<View>(R.id.to_draw_finish_fragment)?.setOnClickListener {
            val action = BlankFragment1Directions.actionBlankFragment1ToDrawFinishFragment()
            it.findNavController().navigate(action)
        }

        val action = BlankFragment1Directions.actionBlankFragment1ToDrawFinishFragment()
        findNavController().navigate(action)

        return inflate
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}