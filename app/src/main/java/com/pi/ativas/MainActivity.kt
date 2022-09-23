package com.pi.ativas

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.pi.ativas.databinding.ActivityMainBinding
import com.pi.ativas.util.LoadToggle


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //bloqueia menu pois ainda não tem ninguém logado
        binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
    }
    fun getDrawerStudent():DrawerLayout{
        toggle = LoadToggle.loadMenu(binding.drawerLayout, this, binding.appBarMain.toolbar)
        val navigationView: NavigationView = binding.navView
        binding.navView.menu.removeGroup(R.id.group_teacher)
        binding.navView.menu.removeItem(R.id.item_teacher_task)
        navigationView.setNavigationItemSelectedListener(this)
        //desbloqueia menu do estudante após login
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        return binding.drawerLayout
    }
    fun getDrawerTeacher():DrawerLayout{
        toggle = LoadToggle.loadMenu(binding.drawerLayout, this, binding.appBarMain.toolbar)
        val navigationView: NavigationView = binding.navView
        binding.navView.menu.removeGroup(R.id.group_student)
        binding.navView.menu.removeItem(R.id.item_student_task)
        navigationView.setNavigationItemSelectedListener(this)
        //desbloqueia menu do professor após login
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        return binding.drawerLayout
    }

    //função para alterar o nome e email que aparece no menu lateral de acordo com o usuário logado
    fun setNavHeader(nomeUsuario:String,emailUsuario:String){
        val txtNameHeader:TextView = binding.navView.getHeaderView(0).findViewById(R.id.nameHeader)
        val txtEmailHeader:TextView = binding.navView.getHeaderView(0).findViewById(R.id.emailHeader)
        txtNameHeader.text = nomeUsuario
        txtEmailHeader.text = emailUsuario
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        menuNavigation(item)
        val drawer = binding.drawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true    }

    //navegação entre fragments através do menu lateral
    private fun menuNavigation(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_student_home -> binding.navHostFragment.findNavController().navigate(R.id.homeStudentFragment)
            R.id.nav_student_rank -> binding.navHostFragment.findNavController().navigate(R.id.rankStudentFragment)
            R.id.nav_student_discipline -> binding.navHostFragment.findNavController().navigate(R.id.disciplineStudentFragment)
            R.id.nav_student_teacher -> binding.navHostFragment.findNavController().navigate(R.id.teacherStudentFragment)
            R.id.nav_student_task_pending -> binding.navHostFragment.findNavController().navigate(R.id.pendingTaskStudentFragment)
            R.id.nav_student_task_history -> binding.navHostFragment.findNavController().navigate(R.id.taskHistoryStudentFragment)
            R.id.nav_teacher_home -> binding.navHostFragment.findNavController().navigate(R.id.homeTeacherFragment)
            R.id.nav_teacher_classes -> binding.navHostFragment.findNavController().navigate(R.id.classTeacherFragment)
            R.id.nav_teacher_profile -> binding.navHostFragment.findNavController().navigate(R.id.profileTeacherFragment)
            R.id.nav_teacher_new_task -> binding.navHostFragment.findNavController().navigate(R.id.newTaskTeacherFragment)
            R.id.nav_teacher_pending_task -> binding.navHostFragment.findNavController().navigate(R.id.pendingTaskTeacherFragment)
            R.id.nav_teacher_task_history -> binding.navHostFragment.findNavController().navigate(R.id.taskHistoryTeacherFragment)
            R.id.nav_support -> binding.navHostFragment.findNavController().navigate(R.id.supportFragment)
            R.id.nav_terms -> binding.navHostFragment.findNavController().navigate(R.id.useTermsFragment)
            R.id.nav_share -> share()
        }
    }
    fun share(){
        val shareIntent = Intent()
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT,R.string.link_do_app)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"subject here")
        startActivity(Intent.createChooser(shareIntent,"Compartilhar aplicativo"))
    }
    override fun onBackPressed() {
        finishAffinity()
    }
}
