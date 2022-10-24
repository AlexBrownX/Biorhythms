package alexbrown.x.biorhythms

import alexbrown.x.biorhythms.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var biorhythmCalculator: BiorhythmCalculator
    lateinit var dateTimeStorage: DateTimeStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        dateTimeStorage = DateTimeStorage(baseContext)
        biorhythmCalculator = BiorhythmCalculator(dateTimeStorage)

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

        if (dateTimeStorage.firstRun) {
            return
        }

        displayDateOfBirth()
        runCalculation()
    }

    fun displayDateOfBirth() {
        val dateTimeTextView = findViewById<TextView>(R.id.textview_date_time)
        dateTimeTextView.text = "Date of birth: ${dateTimeStorage.savedDateTime.format(dateFormatter)}"
    }

    fun runCalculation() {
        val results = biorhythmCalculator.calculate()
        val resultsTextView = findViewById<TextView>(R.id.textview_results)
        resultsTextView.text = "Emotional: ${results.emotionalScore} \nPhysical: ${results.physicalScore} \nIntellectual: ${results.intellectualScore}"
    }

    fun showDateDialogFragment(v: View) {
        DateDialogFragment().show(supportFragmentManager, "DateDialogFragment")
    }

    fun showTimeDialogFragment(v: View) {
        TimeDialogFragment().show(supportFragmentManager, "TimeDialogFragment")
    }
}