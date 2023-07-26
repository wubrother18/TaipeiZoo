package wu.pei.cheng.taipeizoom

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import wu.pei.cheng.taipeizoom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setToolBar("台北市立動物園", false)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_fragment_content_main)

        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun setToolBar(title: String, returnable: Boolean) {
        val toolbar = binding.toolbar
        toolbar.title = title

        if (returnable) {
            toolbar.setNavigationIcon(com.google.android.material.R.drawable.material_ic_keyboard_arrow_previous_black_24dp)
            toolbar.setNavigationOnClickListener {
                navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.popBackStack()
            }
        } else {
            toolbar.setNavigationIcon(com.google.android.material.R.drawable.abc_ic_menu_overflow_material)
        }
    }
}

@BindingAdapter("imgUrl")
fun setPicture(imageView: ImageView, imgUrl: String) {
    Glide.with(imageView.context)
        .load(imgUrl)
        .placeholder(com.google.android.material.R.drawable.mtrl_ic_error)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(imageView)
}