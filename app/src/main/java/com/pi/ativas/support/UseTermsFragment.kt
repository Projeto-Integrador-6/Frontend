package com.pi.ativas.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pi.ativas.MainActivity
import com.pi.ativas.R
import com.pi.ativas.databinding.FragmentUseTermsBinding

class UseTermsFragment : Fragment() {
    private lateinit var binding: FragmentUseTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setTittleAppBar("Termos de Uso")
        binding = FragmentUseTermsBinding.inflate(layoutInflater)
        return binding.root
    }

}