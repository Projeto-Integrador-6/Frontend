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
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //bloqueando o menu para não aparecer na tela de login
        binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)

        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener(this)
    }

    fun setNavHeader(nomeUsuario: String, emailUsuario: String) {
        val txtNameHeader: TextView = binding.navView.getHeaderView(0).findViewById(R.id.nameHeader)
        val txtEmailHeader: TextView =
            binding.navView.getHeaderView(0).findViewById(R.id.emailHeader)
        txtNameHeader.text = nomeUsuario
        txtEmailHeader.text = emailUsuario
    }

    fun getDrawerStudent() {
        LoadToggle.loadMenu(binding.drawerLayout, this, binding.appBarMain.toolbar)
        binding.navView.menu.removeGroup(R.id.group_teacher)
        binding.navView.menu.removeItem(R.id.item_teacher_task)

        //desbloqueia menu do estudante após login
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun getDrawerTeatcher() {
        LoadToggle.loadMenu(binding.drawerLayout, this, binding.appBarMain.toolbar)
        binding.navView.menu.removeGroup(R.id.group_student)
        binding.navView.menu.removeItem(R.id.item_student_task)

        //desbloqueia menu do estudante após login
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return onNavigationItemSelected(item)
    }

    private fun share() {
        val shareIntent = Intent()
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, R.string.link_do_app)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here")
        startActivity(Intent.createChooser(shareIntent, "Compartilhar aplicativo"))
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        with(binding.appBarMain.navHostFragment.findNavController()) {
            when (item.itemId) {
                R.id.nav_student_home -> navigate(R.id.homeStudentFragment)
                R.id.nav_student_rank -> navigate(R.id.rankStudentFragment)
                R.id.nav_student_discipline -> navigate(R.id.disciplineStudentFragment)
                R.id.nav_student_teacher -> navigate(R.id.teacherStudentFragment)
                R.id.nav_student_task_pending -> navigate(R.id.pendingTaskStudentFragment)
                R.id.nav_student_task_history -> navigate(R.id.taskHistoryStudentFragment)
                R.id.nav_teacher_home -> navigate(R.id.homeTeacherFragment)
                R.id.nav_teacher_classes -> navigate(R.id.classTeacherFragment)
                R.id.nav_teacher_profile -> navigate(R.id.profileTeacherFragment)
                R.id.nav_teacher_new_task -> navigate(R.id.newTaskTeacherFragment)
                R.id.nav_teacher_pending_task -> navigate(R.id.pendingTaskTeacherFragment)
                R.id.nav_teacher_task_history -> navigate(R.id.taskHistoryTeacherFragment)
                R.id.nav_support -> navigate(R.id.supportFragment)
                R.id.nav_terms -> navigate(R.id.useTermsFragment)
                R.id.nav_share -> share()
                else -> super.onOptionsItemSelected(item)
                //R.id.nav_share -> share()
            }
        }
        val drawer = binding.drawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}
