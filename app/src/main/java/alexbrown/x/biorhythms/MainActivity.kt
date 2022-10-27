package alexbrown.x.biorhythms

import alexbrown.x.biorhythms.databinding.ActivityMainBinding
import alexbrown.x.biorhythms.fragments.DateDialogFragment
import alexbrown.x.biorhythms.fragments.TimeDialogFragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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

    private val tag = "MainActivity"
    private val aboutMe = "Alex Brown github.com/AlexBrownX"
    private val moonPhaseWebsite = "https://www.icalendar37.net/lunar/app/"
    private val dateDialogFragment = "DateDialogFragment"
    private val timeDialogFragment = "TimeDialogFragment"

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
            R.id.action_biorhythms -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.biorhythms_action)
                return true
            }
            R.id.action_moon_phases -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.moon_phase_action)
                return true
            }
            R.id.action_about -> {
                Toast.makeText(applicationContext, aboutMe, Toast.LENGTH_LONG).show()
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
        Log.d(tag, "Opening date dialog fragment from $v")
        DateDialogFragment().show(supportFragmentManager, dateDialogFragment)
    }

    fun showTimeDialogFragment(v: View) {
        Log.d(tag, "Opening time dialog fragment from $v")
        TimeDialogFragment().show(supportFragmentManager, timeDialogFragment)
    }

    fun showDailyResultActivity(v: View) {
        Log.d(tag, "Opening daily results activity from $v")
        startActivity(Intent(this, DailyResultActivity::class.java))
    }

    fun showWeeklyResultActivity(v: View) {
        Log.d(tag, "Opening weekly results activity from $v")
        startActivity(Intent(this, WeekResultActivity::class.java))
    }

    fun showLongTermResultActivity(v: View) {
        Log.d(tag, "Opening long term results activity from $v")
        startActivity(Intent(this, LongTermResultActivity::class.java))
    }

    fun openMoonPhaseWebsite(v: View) {
        Log.d(tag, "Opening moon phase website from $v")
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(moonPhaseWebsite)))
    }
}