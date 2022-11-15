package com.pi.ativas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.pi.ativas.data.bodys.InsertTaskBody
import com.pi.ativas.databinding.ActivityMainBinding
import com.pi.ativas.util.DATA_USER
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

    fun setTittleAppBar(tittle: String){
        binding.appBarMain.toolbar.title = tittle
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
        binding.navView.menu.setGroupVisible(R.id.group_teacher, false)
        binding.navView.menu.setGroupVisible(R.id.group_student, true)
        //binding.navView.menu.removeItem(R.id.item_teacher_task)

        binding.appBarMain.toolbar.visibility = View.VISIBLE

        //desbloqueia menu do estudante após login
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun getDrawerTeatcher() {
        LoadToggle.loadMenu(binding.drawerLayout, this, binding.appBarMain.toolbar)
        binding.navView.menu.setGroupVisible(R.id.group_student, false)
        binding.navView.menu.setGroupVisible(R.id.group_teacher, true)
        //binding.navView.menu.removeItem(R.id.item_student_task)

        binding.appBarMain.toolbar.visibility = View.VISIBLE

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        /** O que está dentro do with não necessita ser repetido,
         * no caso "binding.appBarMain.navHostFragment.findNavController()"
         * se não colocar desta maneira todos os "navigate(R.id.xxxxxx)"
         * teriam que ter esse trecho de cod antes */

        with(binding.appBarMain.navHostFragment.findNavController()) {
            when (item.itemId) {
                R.id.nav_student_home -> navigateDirect(R.id.homeStudentFragment)
                R.id.nav_student_rank -> navigateDirect(R.id.rankStudentFragment)
                R.id.nav_student_discipline -> navigateDirect(R.id.disciplineStudentFragment)
                R.id.nav_student_teacher -> navigateDirect(R.id.teacherStudentFragment)
                R.id.nav_student_task_pending -> navigateDirect(R.id.pendingTaskStudentFragment)
                R.id.nav_student_task_history -> navigateDirect(R.id.taskHistoryStudentFragment)
                R.id.nav_teacher_home -> navigateDirect(R.id.homeTeacherFragment)
                R.id.nav_teacher_classes -> navigateDirect(R.id.homeTeacherFragment)
                R.id.nav_teacher_profile -> navigateDirect(R.id.profileTeacherFragment)
                R.id.nav_teacher_new_task -> navigateDirect(R.id.newTaskTeacherFragment)
                R.id.nav_teacher_pending_task -> navigateDirect(R.id.pendingTaskTeacherFragment)
                R.id.nav_teacher_task_history -> navigateDirect(R.id.taskHistoryTeacherFragment)
                R.id.nav_support -> navigateDirect(R.id.supportFragment)
                R.id.nav_terms -> navigateDirect(R.id.useTermsFragment)
                R.id.nav_share -> share()
                R.id.nav_exit -> logOff()
                else -> super.onOptionsItemSelected(item)
            }
        }
        val drawer = binding.drawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    //lateinit var insertTaskBody2:InsertTaskBody
    var id:Int=0
    var limit:Int =0
    var classid:Int =0
    /*fun guardTask(insertTaskBody: InsertTaskBody){
        insertTaskBody2 = insertTaskBody
  }*/
    fun guardIdsLimit(idTask:Int, limitMember: Int, classId:Int){
        id=idTask
        limit=limitMember
        classid=classId
        Log.i("TESTE", "guardIdTask: "+id)
        Log.i("TESTE", "guardIdTask: "+classid)
        Log.i("TESTE", "guardIdTask: "+limitMember)
    }
    fun getTaskId2():Int{
        return id
    }
    fun getTaskLimit():Int{
        return limit
    }
    fun getClassId():Int{
        return classid
    }

    private fun logOff() {
        val sharedPreferences = getSharedPreferences(DATA_USER, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        binding.appBarMain.toolbar.visibility = View.INVISIBLE

        navigateDirect(R.id.loginFragment)
    }

    private fun navigateDirect(destination: Int){
        binding.appBarMain.navHostFragment.findNavController().navigate(
            destination, null,
            NavOptions.Builder().setPopUpTo(
                binding.appBarMain.navHostFragment.findNavController().graph.startDestinationId,
                false
            ).build()
        )
    }

}
