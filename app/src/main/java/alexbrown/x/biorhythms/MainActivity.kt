package alexbrown.x.biorhythms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import alexbrown.x.biorhythms.databinding.ActivityMainBinding
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val biorhythmCalculator: BiorhythmCalculator = BiorhythmCalculator()

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setDateAndTimeFromFile()
        runCalculation()
    }

    fun setDateAndTimeFromFile() {
        val dateFile = File(baseContext!!.filesDir, "DateOfBirth")
        if (dateFile.exists()) {
            val dateText = findViewById<TextView>(R.id.textview_date)
            val year = dateFile.readText().split("/")[0]
            val month = Integer.valueOf(dateFile.readText().split("/")[1]) + 1
            val day = dateFile.readText().split("/")[2]

            dateText.text = "$day/$month/$year"
        }

        val timeFile = File(baseContext!!.filesDir, "TimeOfBirth")
        if (timeFile.exists()) {
            val timeText = findViewById<TextView>(R.id.textview_time)
            val hours = timeFile.readText().split("/")[0]
            val minutes = timeFile.readText().split("/")[1]

            timeText.text = "$hours:$minutes"
        }
    }

    fun runCalculation() {
        val dateFile = File(baseContext!!.filesDir, "DateOfBirth")
        val timeFile = File(baseContext!!.filesDir, "TimeOfBirth")

        if (dateFile.exists() && timeFile.exists()) {
            val resultsTextView = findViewById<TextView>(R.id.textview_results)
            val dateTime = getDateTime(dateFile, timeFile)
            val results = biorhythmCalculator._calculate(dateTime)
            resultsTextView.text = "Emotional: ${results.emotionalScore} \nPhysical: ${results.physicalScore} \nIntellectual: ${results.intellectualScore}"
        }
    }

    private fun getDateTime(dateFile: File, timeFile: File): LocalDateTime {
        val savedDate = dateFile.readText()
        val savedTime = timeFile.readText()
        val dateOfBirth = LocalDate.of(Integer.valueOf(savedDate.split("/")[0]), Integer.valueOf(savedDate.split("/")[1]) + 1, Integer.valueOf(savedDate.split("/")[2]))
        val timeOfBirth = LocalTime.of(Integer.valueOf(savedTime.split("/")[0]), Integer.valueOf(savedTime.split("/")[1]) )
        return LocalDateTime.of(dateOfBirth, timeOfBirth)
    }

    fun showDateDialogFragment(v: View) {
        DateDialogFragment().show(supportFragmentManager, "DateDialogFragment")
    }

    fun showTimeDialogFragment(v: View) {
        TimeDialogFragment().show(supportFragmentManager, "TimeDialogFragment")
    }
}