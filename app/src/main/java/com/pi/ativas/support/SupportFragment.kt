package com.pi.ativas.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.databinding.FragmentSupportBinding

class SupportFragment : Fragment() {

    private lateinit var binding: FragmentSupportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setTittleAppBar("Suporte")
       binding = FragmentSupportBinding.inflate(layoutInflater)
        return binding.root
    }


}