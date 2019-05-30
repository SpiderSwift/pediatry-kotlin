package com.develop.grizzzly.pediatrynew.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import com.develop.grizzzly.pediatrynew.R
import com.develop.grizzzly.pediatrynew.adapters.MenuAdapter
import com.develop.grizzzly.pediatrynew.fragments.MessagesFragment
import com.develop.grizzzly.pediatrynew.fragments.NewsFragment
import com.develop.grizzzly.pediatrynew.fragments.TranslationsFragment
import com.develop.grizzzly.pediatrynew.models.NavigationItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),MenuAdapter.OnItemClickListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerList: RecyclerView
    private lateinit var menuTitles: Array<String>
    private lateinit var newsFragment: NewsFragment

    private  val menuList: ArrayList<NavigationItem> = arrayListOf<NavigationItem>(
        NavigationItem(R.drawable.ic_friends, "Друзья"),
        NavigationItem(R.drawable.ic_webinars, "Вебинары"),
        NavigationItem(R.drawable.ic_conferations, "Конференции"),
        NavigationItem(R.drawable.ic_testing, "Тестирование"),
        NavigationItem(R.drawable.ic_details, "Разборы"),
        NavigationItem(R.drawable.ic_support, "Поддержка")
    )

    private val mOnNavigationItemSelectedListener =  BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when (item.itemId){
            R.id.news_btn -> {
                val newsFragment = NewsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.root_container, newsFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.translations_btn -> {
                val translationsFragment  = TranslationsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.root_container, translationsFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.messages_btn -> {
                val messagesFragment = MessagesFragment()
                supportFragmentManager.beginTransaction().replace(R.id.root_container, messagesFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        drawerList = findViewById<RecyclerView>(R.id.left_drawer).apply {
            setHasFixedSize(true)
            adapter = MenuAdapter(menuList, this@MainActivity)
        }

        setSupportActionBar(toolbar)

        newsFragment = NewsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, newsFragment)
            .commit()


        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    /**menu item click listener**/
    override fun onClick(view: View, position: Int){
        actionBar?.title = menuTitles[position]
        drawerLayout.closeDrawer(Gravity.LEFT)
    }
}
