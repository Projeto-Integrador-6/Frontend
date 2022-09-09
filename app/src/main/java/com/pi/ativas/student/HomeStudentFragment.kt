package com.pi.ativas.student

import android.os.Bundle
import android.view.*
import androidx.drawerlayout.widget.DrawerLayout.*
import androidx.fragment.app.Fragment
import com.pi.ativas.MainActivity
import com.pi.ativas.databinding.FragmentHomeStudentBinding


class HomeStudentFragment : Fragment() {

    private lateinit var binding: FragmentHomeStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeStudentBinding.inflate(layoutInflater)
        loadMenu()
        return binding.root
    }
    private fun loadMenu() {
        val activity: MainActivity = getActivity() as MainActivity
        activity.getDrawerStudent().setDrawerLockMode(LOCK_MODE_UNLOCKED)
        activity.setNavHeader("Vinicius crispim de Azevedo","vinicrispim02@hotmail.com")

    }
}