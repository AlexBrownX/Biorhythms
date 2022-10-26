package alexbrown.x.biorhythms

import alexbrown.x.biorhythms.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

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
        return when (item.itemId) {
            R.id.action_about -> {
                Toast.makeText(applicationContext,"Created by Alex Brown",Toast.LENGTH_LONG).show();
                return true
            }
            R.id.action_moon_phase -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun showDateDialogFragment(v: View) {
        DateDialogFragment().show(supportFragmentManager, "DateDialogFragment")
    }

    fun showTimeDialogFragment(v: View) {
        TimeDialogFragment().show(supportFragmentManager, "TimeDialogFragment")
    }

    fun showDailyResultActivity(v: View) {
        startActivity(Intent(this, DailyResultActivity::class.java))
    }

    fun showWeeklyResultActivity(v: View) {
        startActivity(Intent(this, WeekResultActivity::class.java))
    }

    fun showLongTermResultActivity(v: View) {
        startActivity(Intent(this, LongTermResultActivity::class.java))
    }
}