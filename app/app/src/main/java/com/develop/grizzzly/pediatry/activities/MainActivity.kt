package com.develop.grizzzly.pediatry.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController

import androidx.navigation.ui.NavigationUI
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() { //,MenuAdapter.OnItemClickListener {

    //private lateinit var drawerLayout: DrawerLayout
    //private lateinit var drawerList: RecyclerView
    //private lateinit var newsFragment: NewsFragment

    private var currentNavController: LiveData<NavController>? = null



//    private  val menuList: ArrayList<NavigationItem> = arrayListOf<NavigationItem>(
//        NavigationItem(R.drawable.ic_friends, "Друзья", FriendsFragment()),
//        NavigationItem(R.drawable.ic_webinars, "Вебинары", WebinarsFragment()),
//        NavigationItem(R.drawable.ic_conferations, "Конференции", ConferentionsFragment()),
//        NavigationItem(R.drawable.ic_testing, "Тестирование", TestingFragment()),
//        NavigationItem(R.drawable.ic_details, "Разборы", DetailsFragment()),
//        NavigationItem(R.drawable.ic_support, "Поддержка", SupportFragment())
//    )


//    private val mOnNavigationItemSelectedListener =  BottomNavigationView.OnNavigationItemSelectedListener{ item ->
//        when (item.itemId){
//            R.id.news_btn -> {
//                val newsFragment = NewsFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.root_container, newsFragment).commit()
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.translations_btn -> {
//                val translationsFragment  = TranslationsFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.root_container, translationsFragment).commit()
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.messages_btn -> {
//                val messagesFragment = MessagesFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.root_container, messagesFragment).commit()
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        supportActionBar?.hide()
        supportActionBar?.show()
        //bottom_nav.visibility = View.GONE
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }



    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(R.navigation.news_navigation, R.navigation.tranlations_navigation, R.navigation.messages_navigation)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottom_nav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.bottomNavFragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            NavigationUI.setupActionBarWithNavController(this, navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }





    /**menu item click listener**/
    //override fun onClick(view: View, position: Int){
        //drawerLayout.closeDrawer(Gravity.LEFT)
        //supportFragmentManager.beginTransaction().replace(R.id.root_container, menuList[position].fragment).commitAllowingStateLoss()
    //}
}
